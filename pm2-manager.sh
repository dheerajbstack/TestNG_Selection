#!/bin/bash

# PM2 Process Management Script
# Usage: ./pm2-manager.sh [start|stop|restart|status|logs|monit|deploy] [fullstack|backend]

set -e

PROJECT_NAME="enhanced-fullstack-app"
DEPLOYMENT_TYPE=${2:-"fullstack"}

function show_usage() {
    echo "Usage: $0 [command] [deployment_type]"
    echo ""
    echo "Commands:"
    echo "  start     - Start the application"
    echo "  stop      - Stop the application"
    echo "  restart   - Restart the application"
    echo "  reload    - Reload the application (zero-downtime)"
    echo "  status    - Show process status"
    echo "  logs      - Show application logs"
    echo "  monit     - Open monitoring dashboard"
    echo "  deploy    - Full deployment (stop, install, start)"
    echo "  health    - Check application health"
    echo "  cleanup   - Remove all PM2 processes and cleanup"
    echo ""
    echo "Deployment Types:"
    echo "  fullstack - Deploy both frontend and backend (default)"
    echo "  backend   - Deploy backend only"
}

function get_config_file() {
    if [ "$DEPLOYMENT_TYPE" == "backend" ]; then
        echo "ecosystem.backend.config.js"
    else
        echo "ecosystem.fullstack.config.js"
    fi
}

function get_process_name() {
    if [ "$DEPLOYMENT_TYPE" == "backend" ]; then
        echo "backend-only"
    else
        echo "fullstack-dev"
    fi
}

function check_pm2() {
    if ! command -v pm2 &> /dev/null; then
        echo "❌ PM2 is not installed. Installing..."
        npm install -g pm2
    fi
}

function start_app() {
    local config_file=$(get_config_file)
    local process_name=$(get_process_name)
    
    echo "🚀 Starting $process_name ($DEPLOYMENT_TYPE mode)..."
    check_pm2
    
    # Install client dependencies for fullstack deployment
    if [ "$DEPLOYMENT_TYPE" == "fullstack" ] && [ ! -d "client/node_modules" ]; then
        echo "📦 Installing client dependencies..."
        cd client && npm install && cd ..
    fi
    
    pm2 start $config_file
    pm2 status
}

function stop_app() {
    local process_name=$(get_process_name)
    echo "🛑 Stopping $process_name..."
    pm2 stop $process_name 2>/dev/null || echo "Process not running"
}

function restart_app() {
    local process_name=$(get_process_name)
    echo "🔄 Restarting $process_name..."
    pm2 restart $process_name
}

function reload_app() {
    local process_name=$(get_process_name)
    echo "🔄 Reloading $process_name (zero-downtime)..."
    pm2 reload $process_name
}

function show_status() {
    echo "📊 Process Status:"
    pm2 status
    echo ""
    echo "📈 Process Details:"
    pm2 show $PROJECT_NAME 2>/dev/null || echo "Process not found"
}

function show_logs() {
    local process_name=$(get_process_name)
    echo "📝 Showing logs for $process_name..."
    pm2 logs $process_name
}

function open_monit() {
    echo "📊 Opening PM2 monitoring dashboard..."
    pm2 monit
}

function deploy_app() {
    local config_file=$(get_config_file)
    local process_name=$(get_process_name)
    
    echo "🚀 Full deployment of $process_name ($DEPLOYMENT_TYPE mode)..."
    
    # Stop existing processes
    pm2 stop $process_name 2>/dev/null || echo "No existing processes to stop"
    pm2 delete $process_name 2>/dev/null || echo "No existing processes to delete"
    
    # Install dependencies
    echo "📦 Installing dependencies..."
    npm install
    
    # Install client dependencies for fullstack deployment
    if [ "$DEPLOYMENT_TYPE" == "fullstack" ] && [ ! -d "client/node_modules" ]; then
        echo "📦 Installing client dependencies..."
        cd client && npm install && cd ..
    fi
    
    # Start application
    echo "▶️ Starting application..."
    pm2 start $config_file
    
    # Show status
    pm2 status
    
    echo "✅ Deployment completed!"
}

function check_health() {
    local process_name=$(get_process_name)
    echo "🏥 Checking application health..."
    
    # Check if process is running
    if pm2 describe $process_name &>/dev/null; then
        echo "✅ PM2 process is running"
        
        # Check health endpoint (only for backend)
        if [ "$DEPLOYMENT_TYPE" == "backend" ] || [ "$DEPLOYMENT_TYPE" == "fullstack" ]; then
            if curl -s -f http://localhost:5001/health > /dev/null; then
                echo "✅ Health endpoint is responding"
                curl -s http://localhost:5001/health | jq '.' 2>/dev/null || curl -s http://localhost:5001/health
            else
                echo "❌ Health endpoint is not responding"
            fi
        fi
        
        # Check frontend for fullstack deployment
        if [ "$DEPLOYMENT_TYPE" == "fullstack" ]; then
            if curl -s -f http://localhost:3000 > /dev/null; then
                echo "✅ Frontend is responding"
            else
                echo "❌ Frontend is not responding"
            fi
        fi
    else
        echo "❌ PM2 process is not running"
    fi
}

function cleanup() {
    echo "🧹 Cleaning up PM2 processes..."
    pm2 stop all 2>/dev/null || echo "No processes to stop"
    pm2 delete all 2>/dev/null || echo "No processes to delete"
    pm2 kill 2>/dev/null || echo "PM2 daemon not running"
    echo "✅ Cleanup completed"
}

# Main script logic
case "${1:-}" in
    start)
        start_app
        ;;
    stop)
        stop_app
        ;;
    restart)
        restart_app
        ;;
    reload)
        reload_app
        ;;
    status)
        show_status
        ;;
    logs)
        show_logs
        ;;
    monit)
        open_monit
        ;;
    deploy)
        deploy_app
        ;;
    health)
        check_health
        ;;
    cleanup)
        cleanup
        ;;
    *)
        show_usage
        exit 1
        ;;
esac
