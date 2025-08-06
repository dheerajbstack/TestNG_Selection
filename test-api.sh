#!/bin/bash

# Enhanced Full-Stack App - API Testing Script
# This script tests various API endpoints for AI model testing purposes

BASE_URL="http://localhost:5001/api"

echo "🚀 Enhanced Full-Stack App - API Testing Script"
echo "================================================="
echo ""

# Check if server is running
echo "🔍 Checking server health..."
curl -s "$BASE_URL/health" | grep -q "OK" && echo "✅ Server is running" || echo "❌ Server is not running"
echo ""

# Test basic endpoints
echo "📋 Testing basic endpoints..."
echo "GET $BASE_URL"
curl -s "$BASE_URL" | jq '.'
echo ""

# Test users endpoints
echo "👥 Testing users endpoints..."
echo "Creating a test user..."
curl -s -X POST "$BASE_URL/users" \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com","role":"user"}' | jq '.'
echo ""

echo "Getting all users..."
curl -s "$BASE_URL/users" | jq '.'
echo ""

# Test products endpoints
echo "📦 Testing products endpoints..."
echo "Creating a test product..."
curl -s -X POST "$BASE_URL/products" \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Product","price":99.99,"category":"Test","stock":10,"description":"A test product"}' | jq '.'
echo ""

echo "Getting products with filters..."
curl -s "$BASE_URL/products?category=Test&sort=price_asc" | jq '.'
echo ""

# Test tasks endpoints
echo "📋 Testing tasks endpoints..."
echo "Creating a test task..."
curl -s -X POST "$BASE_URL/tasks" \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Task","priority":"high","assignedTo":1}' | jq '.'
echo ""

# Test search functionality
echo "🔍 Testing search functionality..."
echo "Searching for 'test'..."
curl -s "$BASE_URL/search?q=test" | jq '.'
echo ""

# Test analytics
echo "📊 Testing analytics..."
curl -s "$BASE_URL/analytics" | jq '.'
echo ""

# Test AI-specific endpoints
echo "🤖 Testing AI-specific endpoints..."

echo "Testing slow response (2 second delay)..."
curl -s "$BASE_URL/test/slow?delay=2000" | jq '.'
echo ""

echo "Testing error generation (404)..."
curl -s "$BASE_URL/test/error/404" | jq '.'
echo ""

echo "Testing large data generation (100 items)..."
curl -s "$BASE_URL/test/large-data?count=100" | jq '.count, .data[0]'
echo ""

echo "Testing random data generation..."
curl -s "$BASE_URL/test/random?type=object" | jq '.'
echo ""

echo "Testing echo endpoint..."
curl -s -X POST "$BASE_URL/test/echo" \
  -H "Content-Type: application/json" \
  -d '{"test":"data","timestamp":"'$(date -u +%Y-%m-%dT%H:%M:%S.%3NZ)'"}' | jq '.'
echo ""

# Test pagination
echo "📄 Testing pagination..."
curl -s "$BASE_URL/users/paginated?page=1&limit=2" | jq '.'
echo ""

echo "✅ API testing completed!"
echo ""
echo "💡 Tips for AI model testing:"
echo "   - Use /api/test/slow for timeout testing"
echo "   - Use /api/test/error/:code for error handling"
echo "   - Use /api/test/large-data for performance testing"
echo "   - Use /api/search for natural language processing"
echo "   - Use /api/analytics for data aggregation testing"
