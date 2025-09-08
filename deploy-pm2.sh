#!/bin/bash

# PM2 Deployment Script for Enhanced Full-Stack App
# This script helps deploy the Node.js application using PM2

set -e

echo "🚀 Starting PM2 deployment for Enhanced Full-Stack App..."

# Parse command line arguments
DEPLOYMENT_TYPE=${1:-"fullstack"}

if [[ "$DEPLOYMENT_TYPE" != "fullstack" && "$DEPLOYMENT_TYPE" != "backend" ]]; then
    echo "Usage: $0 [fullstack|backend]"
    echo "  fullstack - Deploy both frontend and backend (default)"
    echo "  backend   - Deploy backend only"
    exit 1
fi

echo "📋 Deployment type: $DEPLOYMENT_TYPE"

# Check if PM2 is installed
if ! command -v pm2 &> /dev/null; then
    echo "❌ PM2 is not installed. Installing PM2 globally..."
    npm install -g pm2
fi

# Install dependencies if node_modules doesn't exist
if [ ! -d "node_modules" ]; then
    echo "📦 Installing dependencies..."
    npm install
fi

# Install client dependencies for fullstack deployment
if [ "$DEPLOYMENT_TYPE" == "fullstack" ] && [ ! -d "client/node_modules" ]; then
    echo "📦 Installing client dependencies..."
    cd client && npm install && cd ..
fi

# Copy environment file if it doesn't exist
if [ ! -f ".env" ]; then
    echo "📄 Creating .env file from .env.example..."
    cp .env.example .env
    echo "⚠️  Please update .env file with your specific configuration"
fi

# Create logs directory if it doesn't exist
mkdir -p logs

echo "🛑 Stopping existing PM2 processes..."
pm2 stop all 2>/dev/null || echo "No existing processes to stop"

echo "🗑️  Deleting existing PM2 processes..."
pm2 delete all 2>/dev/null || echo "No existing processes to delete"

if [ "$DEPLOYMENT_TYPE" == "fullstack" ]; then
    echo "▶️  Starting fullstack application with PM2..."
    echo "   This will start both frontend (React) and backend (Node.js) servers"
    pm2 start ecosystem.config.js
else
    echo "▶️  Starting backend-only application with PM2..."
    echo "   This will start only the backend (Node.js) server"
    pm2 start ecosystem.config.js
fi

echo "📊 PM2 process status:"
pm2 status

echo ""
echo "📝 Management commands:"
if [ "$DEPLOYMENT_TYPE" == "fullstack" ]; then
    echo "🔄 To restart: pm2 restart fullstack-dev"
    echo "� To stop: pm2 stop fullstack-dev"
    echo "🌐 Frontend: http://localhost:3000"
    echo "🌐 Backend API: http://localhost:5001/api"
else
    echo "🔄 To restart: pm2 restart backend-only"
    echo "🛑 To stop: pm2 stop backend-only"
    echo "🌐 Backend API: http://localhost:5001/api"
    echo "🏥 Health check: http://localhost:5001/health"
fi

echo "📝 To view logs: npm run pm2:logs"
echo "📊 To monitor: npm run pm2:monit"

echo "✅ PM2 deployment completed successfully!"
