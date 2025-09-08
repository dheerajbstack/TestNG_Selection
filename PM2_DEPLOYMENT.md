# PM2 Deployment Guide

This project includes PM2 configuration for production-ready deployment of the Node.js backend service.

## Prerequisites

- Node.js (v14 or higher)
- npm or yarn
- PM2 (will be installed automatically)

## Quick Start with PM2

### 1. One-Command Deployment
```bash
# Run the automated deployment script
./deploy-pm2.sh
```

### 2. Manual Deployment Steps

#### Install Dependencies
```bash
npm install
```

#### Install PM2 Globally (if not already installed)
```bash
npm install -g pm2
```

#### Configure Environment
```bash
# Copy the example environment file
cp .env.example .env

# Edit the .env file with your specific configuration
nano .env
```

#### Start with PM2
```bash
# Start the application
npm run pm2:start

# Or directly with PM2
pm2 start ecosystem.config.js
```

## PM2 Management Commands

| Command | Description |
|---------|-------------|
| `npm run pm2:start` | Start the application with PM2 |
| `npm run pm2:stop` | Stop the application |
| `npm run pm2:restart` | Restart the application |
| `npm run pm2:reload` | Reload the application (zero-downtime) |
| `npm run pm2:delete` | Delete the application from PM2 |
| `npm run pm2:status` | Show PM2 process status |
| `npm run pm2:logs` | Show application logs |
| `npm run pm2:monit` | Open PM2 monitoring dashboard |

## PM2 Configuration

The PM2 configuration is defined in `ecosystem.config.js`:

### Key Features
- **Auto-restart**: Automatically restarts on crashes
- **Memory monitoring**: Restarts if memory usage exceeds 1GB
- **Logging**: Structured logging with rotation
- **Environment management**: Separate dev/production configs
- **Health checks**: Built-in health monitoring
- **Graceful shutdown**: Proper cleanup on stop

### Environment Variables
- `NODE_ENV`: Set to 'development' or 'production'
- `PORT`: Server port (default: 5001)
- See `.env.example` for all available variables

## Monitoring and Logs

### View Real-time Logs
```bash
pm2 logs enhanced-fullstack-app
```

### Monitor Resource Usage
```bash
pm2 monit
```

### View Process Details
```bash
pm2 show enhanced-fullstack-app
```

## Production Deployment

### For Production Environment
```bash
# Start with production environment
pm2 start ecosystem.config.js --env production
```

### Enable PM2 Startup (Auto-start on server reboot)
```bash
# Generate startup script
pm2 startup

# Save current process list
pm2 save
```

### Log Management
```bash
# Install PM2 log rotate module
pm2 install pm2-logrotate

# Configure log rotation (optional)
pm2 set pm2-logrotate:max_size 10M
pm2 set pm2-logrotate:retain 7
```

## Troubleshooting

### Common Issues

1. **Port already in use**
   ```bash
   # Check what's using the port
   lsof -i :5001
   
   # Kill the process or change PORT in .env
   ```

2. **Permission errors**
   ```bash
   # Fix permissions for PM2
   sudo chown -R $(whoami) ~/.pm2
   ```

3. **Module not found errors**
   ```bash
   # Reinstall dependencies
   rm -rf node_modules package-lock.json
   npm install
   ```

### Debugging
```bash
# Check PM2 daemon logs
pm2 logs pm2

# Restart PM2 daemon
pm2 kill
pm2 resurrect
```

## Advanced Configuration

### Cluster Mode (Multiple Instances)
To run multiple instances for load balancing:

```javascript
// In ecosystem.config.js
{
  instances: 'max', // or number like 4
  exec_mode: 'cluster'
}
```

### Custom Health Checks
```javascript
// In ecosystem.config.js
{
  health_check_http_url: 'http://localhost:5001/health',
  health_check_grace_period: 3000
}
```

## Integration with CI/CD

### Example GitHub Actions
```yaml
- name: Deploy with PM2
  run: |
    npm install
    pm2 reload ecosystem.config.js --env production
```

### Example Docker Integration
```dockerfile
RUN npm install -g pm2
CMD ["pm2-runtime", "start", "ecosystem.config.js", "--env", "production"]
```

## Security Considerations

1. **Environment Variables**: Never commit `.env` files
2. **Process User**: Run PM2 with non-root user in production
3. **Firewall**: Configure firewall rules for port access
4. **SSL/TLS**: Use reverse proxy (nginx) for SSL termination

## Performance Tuning

### Memory Optimization
```javascript
// In ecosystem.config.js
{
  node_args: '--max-old-space-size=4096',
  max_memory_restart: '1G'
}
```

### CPU Optimization
```javascript
{
  instances: require('os').cpus().length,
  exec_mode: 'cluster'
}
```

For more information, visit the [PM2 Documentation](https://pm2.keymetrics.io/docs/).
