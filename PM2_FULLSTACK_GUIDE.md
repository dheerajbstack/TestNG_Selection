# 🚀 PM2 Full-Stack Deployment Guide

Your Node.js service now supports **both full-stack and backend-only** deployment using PM2.

## 🎯 Deployment Options

### 1. Full-Stack Deployment (Frontend + Backend)
Runs both React frontend and Node.js backend concurrently:
```bash
# Quick deployment
./deploy-pm2.sh fullstack

# Or using npm scripts
npm run pm2:start:fullstack
```

### 2. Backend-Only Deployment
Runs only the Node.js backend server:
```bash
# Quick deployment
./deploy-pm2.sh backend

# Or using npm scripts
npm run pm2:start:backend
```

## 📁 Configuration Files

| File | Purpose |
|------|---------|
| `ecosystem.fullstack.config.js` | Full-stack deployment (frontend + backend) |
| `ecosystem.backend.config.js` | Backend-only deployment |
| `ecosystem.config.js` | Main config with both app definitions |

## 🛠️ Available Commands

### NPM Scripts:
```bash
npm run pm2:start:fullstack    # Start full-stack app
npm run pm2:start:backend      # Start backend only
npm run pm2:start              # Start both (default config)
npm run pm2:stop               # Stop all processes
npm run pm2:restart            # Restart all processes
npm run pm2:status             # Show process status
npm run pm2:logs               # View logs
npm run pm2:monit              # Open monitoring dashboard
```

### Direct Scripts:
```bash
./deploy-pm2.sh [fullstack|backend]         # Automated deployment
./pm2-manager.sh [command] [fullstack|backend]  # Advanced management
```

## 🌐 Service Endpoints

### Full-Stack Mode:
- **Frontend (React)**: http://localhost:3000
- **Backend API**: http://localhost:5001/api
- **Health Check**: http://localhost:5001/health

### Backend-Only Mode:
- **Backend API**: http://localhost:5001/api
- **Health Check**: http://localhost:5001/health

## 🚀 Quick Start Examples

### Example 1: Full Development Environment
```bash
# Deploy both frontend and backend
./deploy-pm2.sh fullstack

# Check status
npm run pm2:status

# View logs
npm run pm2:logs

# Monitor processes
npm run pm2:monit
```

### Example 2: API Server Only
```bash
# Deploy only the backend
./deploy-pm2.sh backend

# Check health
curl http://localhost:5001/health

# View backend logs
pm2 logs backend-only
```

### Example 3: Using PM2 Manager
```bash
# Advanced management with specific deployment type
./pm2-manager.sh deploy fullstack
./pm2-manager.sh health fullstack
./pm2-manager.sh restart backend
```

## 📊 Process Management

### Full-Stack Processes:
- **Process Name**: `fullstack-dev`
- **Command**: `npm run dev` (runs concurrently)
- **Ports**: 3000 (frontend), 5001 (backend)

### Backend-Only Process:
- **Process Name**: `backend-only`
- **Command**: `node server/index.js`
- **Port**: 5001

## 🔧 Configuration Details

### Full-Stack Config (`ecosystem.fullstack.config.js`):
- Uses `npm run dev` command
- Runs both frontend and backend
- Higher memory limit (2GB)
- Longer timeout for startup
- Combined logging

### Backend Config (`ecosystem.backend.config.js`):
- Direct Node.js execution
- Health check monitoring
- Lower memory usage (1GB)
- Production-ready settings

## 📝 Logging

### Log Files Location: `logs/`
- `fullstack-dev.log` - Full-stack app logs
- `backend-only.log` - Backend-only app logs
- `*-out.log` - Standard output
- `*-error.log` - Error logs

### View Logs:
```bash
# All logs
npm run pm2:logs

# Specific process logs
pm2 logs fullstack-dev
pm2 logs backend-only

# Follow logs in real-time
pm2 logs --lines 100
```

## 🔍 Health Monitoring

### Health Check Endpoint:
```bash
curl http://localhost:5001/health
```

### Response Example:
```json
{
  "uptime": 123.456,
  "timestamp": "2025-09-05T10:30:00.000Z",
  "status": "OK",
  "pid": 12345,
  "memory": {
    "rss": 45678912,
    "heapTotal": 12345678,
    "heapUsed": 8765432
  },
  "environment": "development",
  "version": "2.0.0"
}
```

## 🛠️ Troubleshooting

### Common Issues:

1. **Port conflicts**:
   ```bash
   # Check what's using the ports
   lsof -i :3000
   lsof -i :5001
   ```

2. **Client dependencies missing**:
   ```bash
   cd client && npm install
   ```

3. **PM2 permission issues**:
   ```bash
   sudo chown -R $(whoami) ~/.pm2
   ```

4. **Process not starting**:
   ```bash
   # Check PM2 logs
   pm2 logs
   
   # Check configuration
   node -e "console.log(require('./ecosystem.fullstack.config.js'))"
   ```

## 🔄 Development Workflow

### Development with Hot Reload:
```bash
# Start full-stack with hot reload
./deploy-pm2.sh fullstack

# Make changes to code
# Both frontend and backend will auto-reload
```

### Production Backend Only:
```bash
# Deploy backend for production
NODE_ENV=production ./deploy-pm2.sh backend

# Or with PM2 environment
pm2 start ecosystem.backend.config.js --env production
```

## 📈 Performance Tips

1. **Memory Optimization**:
   - Full-stack: 2GB limit (handles both processes)
   - Backend-only: 1GB limit (single process)

2. **Log Management**:
   ```bash
   # Install log rotation
   pm2 install pm2-logrotate
   
   # Configure rotation
   pm2 set pm2-logrotate:max_size 10M
   pm2 set pm2-logrotate:retain 30
   ```

3. **Monitoring**:
   ```bash
   # Real-time monitoring
   pm2 monit
   
   # Process details
   pm2 show fullstack-dev
   pm2 show backend-only
   ```

## 🔒 Security Notes

- Environment variables are loaded from `.env` file
- Health check endpoint exposes system information
- Use reverse proxy (nginx) for production SSL/TLS
- Firewall configuration for port access

## 🎯 Next Steps

1. **Configure Environment**:
   ```bash
   cp .env.example .env
   # Edit .env with your settings
   ```

2. **Choose Deployment Type**:
   - Full-stack for development
   - Backend-only for API server

3. **Set Up Monitoring**:
   ```bash
   npm run pm2:monit
   ```

4. **Production Setup**:
   - Use nginx reverse proxy
   - Configure SSL certificates
   - Set up proper logging
   - Configure firewall rules

Your application is now ready for both development and production deployment! 🎉
