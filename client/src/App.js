import React, { useState, useEffect } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './App.css';

function App() {
  const [activeTab, setActiveTab] = useState('dashboard');
  const [message, setMessage] = useState('');
  const [users, setUsers] = useState([]);
  const [products, setProducts] = useState([]);
  const [tasks, setTasks] = useState([]);
  const [orders, setOrders] = useState([]);
  const [analytics, setAnalytics] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [searchResults, setSearchResults] = useState(null);
  const [loading, setLoading] = useState(false);
  const [health, setHealth] = useState(null);

  // Form states
  const [newUser, setNewUser] = useState({ name: '', email: '', role: 'user' });
  const [newProduct, setNewProduct] = useState({ name: '', price: '', category: '', stock: '', description: '' });
  const [newTask, setNewTask] = useState({ title: '', priority: 'medium', assignedTo: '' });
  const [newOrder, setNewOrder] = useState({ userId: '', productId: '', quantity: 1 });
  
  // File upload and theme states
  const [selectedFile, setSelectedFile] = useState(null);
  const [backgroundTheme, setBackgroundTheme] = useState('default');
  const [uploadedFiles, setUploadedFiles] = useState([]);
  const [hasImageBackground, setHasImageBackground] = useState(false);

  // Fetch initial data when component mounts
  useEffect(() => {
    fetchMessage();
    fetchUsers();
    fetchProducts();
    fetchTasks();
    fetchOrders();
    fetchAnalytics();
    checkHealth();
  }, []);

  // Initialize default theme on mount
  useEffect(() => {
    const body = document.body;
    body.className = body.className.replace(/theme-\w+/g, '');
    body.classList.add('theme-default');
  }, []);

  const fetchMessage = async () => {
    try {
      const response = await fetch('/api');
      const data = await response.json();
      setMessage(data.message);
    } catch (error) {
      console.error('Error fetching message:', error);
      setMessage('Error connecting to backend');
    }
  };

  const fetchUsers = async () => {
    try {
      const response = await fetch('/api/users');
      const data = await response.json();
      setUsers(data.users || data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  const fetchProducts = async () => {
    try {
      console.log('Fetching products from /api/products');
      const response = await fetch('/api/products');
      console.log('Products response status:', response.status);
      
      const data = await response.json();
      console.log('Products data received:', data);
      
      setProducts(data.products || data);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const fetchTasks = async () => {
    try {
      const response = await fetch('/api/tasks');
      const data = await response.json();
      setTasks(data.tasks || data);
    } catch (error) {
      console.error('Error fetching tasks:', error);
    }
  };

  const fetchOrders = async () => {
    try {
      const response = await fetch('/api/orders');
      const data = await response.json();
      setOrders(data.orders || data);
    } catch (error) {
      console.error('Error fetching orders:', error);
    }
  };

  const fetchAnalytics = async () => {
    try {
      const response = await fetch('/api/analytics');
      const data = await response.json();
      setAnalytics(data);
    } catch (error) {
      console.error('Error fetching analytics:', error);
    }
  };

  const checkHealth = async () => {
    try {
      const response = await fetch('/api/health');
      const data = await response.json();
      setHealth(data);
    } catch (error) {
      console.error('Error checking health:', error);
    }
  };

  const handleSearch = async () => {
    if (!searchQuery.trim()) return;
    
    try {
      setLoading(true);
      const response = await fetch(`/api/search?q=${encodeURIComponent(searchQuery)}`);
      const data = await response.json();
      setSearchResults(data);
    } catch (error) {
      console.error('Error searching:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleAddUser = async (e) => {
    e.preventDefault();
    if (!newUser.name || !newUser.email) return;

    try {
      setLoading(true);
      const response = await fetch('/api/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newUser),
      });
      
      if (response.ok) {
        setNewUser({ name: '', email: '', role: 'user' });
        fetchUsers();
        fetchAnalytics();
        toast.success('User added successfully!');
      } else {
        const error = await response.json();
        toast.error(`Error: ${error.error}`);
      }
    } catch (error) {
      console.error('Error adding user:', error);
      toast.error('Error adding user');
    } finally {
      setLoading(false);
    }
  };

  const handleAddProduct = async (e) => {
    e.preventDefault();
    console.log('Adding product:', newProduct);
    
    if (!newProduct.name || !newProduct.price || !newProduct.category) {
      console.log('Missing required fields');
      toast.error('Please fill in all required fields: name, price, and category');
      return;
    }

    try {
      setLoading(true);
      console.log('Making API call to:', `/api/products`);
      
      const response = await fetch(`/api/products`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newProduct),
      });
      
      console.log('Response status:', response.status);
      
      if (response.ok) {
        const result = await response.json();
        console.log('Product created successfully:', result);
        setNewProduct({ name: '', price: '', category: '', stock: '', description: '' });
        fetchProducts();
        fetchAnalytics();
        toast.success('Product added successfully!');
      } else {
        const error = await response.json();
        console.error('API Error:', error);
        toast.error(`Error: ${error.error}`);
      }
    } catch (error) {
      console.error('Error adding product:', error);
      toast.error('Error adding product: ' + error.message);
    } finally {
      setLoading(false);
    }
  };

  const handleAddTask = async (e) => {
    e.preventDefault();
    if (!newTask.title) return;

    try {
      setLoading(true);
      const response = await fetch('/api/tasks', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTask),
      });
      
      if (response.ok) {
        setNewTask({ title: '', priority: 'medium', assignedTo: '' });
        fetchTasks();
        fetchAnalytics();
        toast.success('Task added successfully!');
      }
    } catch (error) {
      console.error('Error adding task:', error);
      toast.error('Error adding task');
    } finally {
      setLoading(false);
    }
  };

  const handleAddOrder = async (e) => {
    e.preventDefault();
    if (!newOrder.userId || !newOrder.productId) return;

    try {
      setLoading(true);
      const response = await fetch('/api/orders', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newOrder),
      });
      
      if (response.ok) {
        setNewOrder({ userId: '', productId: '', quantity: 1 });
        fetchOrders();
        fetchProducts(); // Refresh to update stock
        fetchAnalytics();
        toast.success('Order created successfully!');
      } else {
        const error = await response.json();
        toast.error(`Error: ${error.error}`);
      }
    } catch (error) {
      console.error('Error creating order:', error);
      toast.error('Error creating order');
    } finally {
      setLoading(false);
    }
  };

  const handleToggleTask = async (taskId, completed) => {
    try {
      const response = await fetch(`/api/tasks/${taskId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ completed: !completed }),
      });
      
      if (response.ok) {
        fetchTasks();
        fetchAnalytics();
      }
    } catch (error) {
      console.error('Error updating task:', error);
    }
  };

  const handleDeleteUser = async (userId) => {
    if (!window.confirm('Are you sure you want to delete this user?')) return;
    
    try {
      const response = await fetch(`/api/users/${userId}`, {
        method: 'DELETE',
      });
      
      if (response.ok) {
        fetchUsers();
        fetchAnalytics();
        toast.success('User deleted successfully!');
      }
    } catch (error) {
      console.error('Error deleting user:', error);
      toast.error('Error deleting user');
    }
  };

  const handleFileUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
      // Check file size (limit to 5MB)
      if (file.size > 5 * 1024 * 1024) {
        toast.error('File size must be less than 5MB');
        return;
      }
      
      // Check file type
      const allowedTypes = ['image/jpeg', 'image/png', 'image/gif', 'text/plain', 'application/pdf'];
      if (!allowedTypes.includes(file.type)) {
        toast.error('Only JPEG, PNG, GIF, TXT, and PDF files are allowed');
        return;
      }
      
      setSelectedFile(file);
      
      // Automatically set theme to "image" if it's an image file
      if (file.type.startsWith('image/')) {
        setBackgroundTheme('image');
        toast.success(`Image "${file.name}" selected - will be set as background!`);
      } else {
        toast.success(`File "${file.name}" selected successfully!`);
      }
    }
  };

  const handleFileSubmit = async (e) => {
    e.preventDefault();
    
    if (!selectedFile) {
      toast.error('Please select a file first');
      return;
    }

    try {
      setLoading(true);
      
      // Create a preview URL for the file
      const fileURL = URL.createObjectURL(selectedFile);
      
      // Create a FormData object to handle file upload
      const formData = new FormData();
      formData.append('file', selectedFile);
      formData.append('backgroundTheme', backgroundTheme);
      
      // For demo purposes, we'll simulate file upload
      // In a real app, you'd send this to your backend
      await new Promise(resolve => setTimeout(resolve, 2000)); // Simulate upload delay
      
      // Add to uploaded files list
      const newUploadedFile = {
        id: Date.now(),
        name: selectedFile.name,
        size: selectedFile.size,
        type: selectedFile.type,
        backgroundTheme: backgroundTheme,
        uploadedAt: new Date().toISOString(),
        fileURL: fileURL, // Store the preview URL
        isImage: selectedFile.type.startsWith('image/')
      };
      
      setUploadedFiles(prev => [...prev, newUploadedFile]);
      setSelectedFile(null);
      toast.success('File uploaded successfully!');
      
      // Automatically apply image as background if it's an image file
      if (newUploadedFile.isImage) {
        setBackgroundTheme('image');
        applyImageBackground(fileURL);
        toast.info('Image automatically set as background!');
      }
      
      // Reset file input
      const fileInput = document.getElementById('fileInput');
      if (fileInput) fileInput.value = '';
      
    } catch (error) {
      console.error('Error uploading file:', error);
      toast.error('Error uploading file');
    } finally {
      setLoading(false);
    }
  };

  const handleThemeChange = (theme) => {
    setBackgroundTheme(theme);
    
    // Apply theme to body
    const body = document.body;
    const appHeader = document.querySelector('.App-header');
    
    if (hasImageBackground) {
      // When image background is active, themes control text overlay styling
      applyTextThemeOverlay(theme, appHeader);
    } else {
      // Normal theme behavior - control full background
      // Clear all existing theme classes and styles
      body.className = body.className.replace(/theme-\w+/g, '');
      body.style.backgroundImage = '';
      body.style.backgroundSize = '';
      body.style.backgroundPosition = '';
      body.style.backgroundRepeat = '';
      body.style.backgroundAttachment = '';
      
      // Reset header styles
      if (appHeader) {
        appHeader.style.background = '';
        appHeader.style.backdropFilter = '';
        appHeader.style.color = '';
      }
      
      if (theme !== 'image') {
        // Apply the selected color/gradient theme
        body.classList.add(`theme-${theme}`);
        
        // Set appropriate header styles for each theme
        if (appHeader) {
          switch(theme) {
            case 'light':
              appHeader.style.background = 'linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%)';
              appHeader.style.color = '#1e293b';
              break;
            case 'blue':
              appHeader.style.background = 'linear-gradient(135deg, rgba(30, 58, 138, 0.9) 0%, rgba(59, 130, 246, 0.9) 100%)';
              break;
            case 'green':
              appHeader.style.background = 'linear-gradient(135deg, rgba(22, 101, 52, 0.9) 0%, rgba(16, 185, 129, 0.9) 100%)';
              break;
            case 'purple':
              appHeader.style.background = 'linear-gradient(135deg, rgba(124, 58, 237, 0.9) 0%, rgba(168, 85, 247, 0.9) 100%)';
              break;
            case 'gradient':
              appHeader.style.background = 'rgba(0, 0, 0, 0.7)';
              appHeader.style.backdropFilter = 'blur(10px)';
              break;
            default:
              appHeader.style.background = 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)';
          }
        }
      }
    }
    
    const themeDisplayName = hasImageBackground 
      ? `${theme} text overlay` 
      : (theme === 'image' ? 'Image Background' : theme);
    
    toast.success(`Theme changed to ${themeDisplayName}`);
  };

  const applyTextThemeOverlay = (theme, appHeader) => {
    if (!appHeader) return;
    
    // Keep the background image but change text overlay styling
    switch(theme) {
      case 'light':
        appHeader.style.background = 'rgba(255, 255, 255, 0.9)';
        appHeader.style.color = '#1e293b';
        appHeader.style.backdropFilter = 'blur(10px)';
        break;
      case 'dark':
      case 'default':
        appHeader.style.background = 'rgba(0, 0, 0, 0.8)';
        appHeader.style.color = 'white';
        appHeader.style.backdropFilter = 'blur(10px)';
        break;
      case 'blue':
        appHeader.style.background = 'rgba(30, 58, 138, 0.9)';
        appHeader.style.color = 'white';
        appHeader.style.backdropFilter = 'blur(5px)';
        break;
      case 'green':
        appHeader.style.background = 'rgba(22, 101, 52, 0.9)';
        appHeader.style.color = 'white';
        appHeader.style.backdropFilter = 'blur(5px)';
        break;
      case 'purple':
        appHeader.style.background = 'rgba(124, 58, 237, 0.9)';
        appHeader.style.color = 'white';
        appHeader.style.backdropFilter = 'blur(5px)';
        break;
      case 'gradient':
        appHeader.style.background = 'linear-gradient(45deg, rgba(255, 107, 107, 0.8), rgba(255, 217, 61, 0.8), rgba(78, 205, 196, 0.8), rgba(69, 183, 209, 0.8))';
        appHeader.style.color = 'white';
        appHeader.style.backdropFilter = 'blur(8px)';
        break;
      default:
        appHeader.style.background = 'rgba(0, 0, 0, 0.7)';
        appHeader.style.color = 'white';
        appHeader.style.backdropFilter = 'blur(5px)';
    }
  };

  const applyImageBackground = (imageURL) => {
    const body = document.body;
    const appHeader = document.querySelector('.App-header');
    
    // Clear theme classes but keep background image
    body.className = body.className.replace(/theme-\w+/g, '');
    body.style.backgroundImage = `url(${imageURL})`;
    body.style.backgroundSize = 'cover';
    body.style.backgroundPosition = 'center';
    body.style.backgroundRepeat = 'no-repeat';
    body.style.backgroundAttachment = 'fixed';
    
    // Set image background mode
    setHasImageBackground(true);
    
    // Apply current theme as text overlay
    applyTextThemeOverlay(backgroundTheme, appHeader);
    
    toast.success('Image background applied!');
  };

  const applyFileAsBackground = (file) => {
    if (file.isImage && file.fileURL) {
      setBackgroundTheme('image');
      applyImageBackground(file.fileURL);
    } else {
      handleThemeChange(file.backgroundTheme);
    }
  };

  const handleDeleteFile = (fileId) => {
    console.log('Delete file clicked for ID:', fileId);
    
    if (!window.confirm('Are you sure you want to delete this file?')) return;
    
    console.log('User confirmed deletion');
    
    // Find the file to clean up its URL
    const fileToDelete = uploadedFiles.find(file => file.id === fileId);
    console.log('File to delete:', fileToDelete);
    
    if (fileToDelete && fileToDelete.fileURL) {
      URL.revokeObjectURL(fileToDelete.fileURL);
      
      // If this is the current background image, reset to normal themes
      if (fileToDelete.isImage && hasImageBackground) {
        console.log('Removing image background');
        const body = document.body;
        body.style.backgroundImage = '';
        body.style.backgroundSize = '';
        body.style.backgroundPosition = '';
        body.style.backgroundRepeat = '';
        body.style.backgroundAttachment = '';
        
        setHasImageBackground(false);
        
        // Apply current theme as normal background theme
        setTimeout(() => {
          handleThemeChange(backgroundTheme);
        }, 100);
        
        toast.info('Background image removed, switched to theme mode');
      }
    }
    
    setUploadedFiles(prev => {
      const newFiles = prev.filter(file => file.id !== fileId);
      console.log('Updated files list:', newFiles);
      return newFiles;
    });
    
    toast.success('File deleted successfully!');
  };

  const formatFileSize = (bytes) => {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>🚀 Enhanced Full-Stack React App</h1>
        
        {/* Navigation */}
        <nav className="nav-tabs">
          {['dashboard', 'users', 'products', 'tasks', 'orders', 'search', 'files'].map(tab => (
            <button
              key={tab}
              className={`nav-tab ${activeTab === tab ? 'active' : ''}`}
              onClick={() => setActiveTab(tab)}
            >
              {tab === 'files' ? 'Files & Themes' : tab.charAt(0).toUpperCase() + tab.slice(1)}
            </button>
          ))}
        </nav>

        {/* Dashboard Tab */}
        {activeTab === 'dashboard' && (
          <div className="tab-content">
            {/* Backend Connection Status */}
            <div className="status-section">
              <h2>Backend Status</h2>
              <p className="message">{message}</p>
              {health && (
                <div className="health-info">
                  <p>Status: <span className="status-ok">{health.status}</span></p>
                  <p>Uptime: {Math.floor(health.uptime)} seconds</p>
                </div>
              )}
            </div>

            {/* Analytics */}
            {analytics && (
              <div className="analytics-section">
                <h2>📊 Analytics Dashboard</h2>
                <div className="analytics-grid">
                  <div className="analytics-card">
                    <h3>👥 Users</h3>
                    <p>Total: {analytics.users.total}</p>
                    <p>Admins: {analytics.users.admins}</p>
                    <p>Regular: {analytics.users.regular}</p>
                  </div>
                  <div className="analytics-card">
                    <h3>📦 Products</h3>
                    <p>Total: {analytics.products.total}</p>
                    <p>Categories: {analytics.products.categories.length}</p>
                    <p>Low Stock: {analytics.products.lowStock}</p>
                  </div>
                  <div className="analytics-card">
                    <h3>📋 Tasks</h3>
                    <p>Total: {analytics.tasks.total}</p>
                    <p>Completed: {analytics.tasks.completed}</p>
                    <p>High Priority: {analytics.tasks.highPriority}</p>
                  </div>
                  <div className="analytics-card">
                    <h3>🛒 Orders</h3>
                    <p>Total: {analytics.orders.total}</p>
                    <p>Pending: {analytics.orders.pending}</p>
                    <p>Revenue: ${analytics.orders.totalRevenue.toFixed(2)}</p>
                  </div>
                </div>
              </div>
            )}
          </div>
        )}

        {/* Users Tab */}
        {activeTab === 'users' && (
          <div className="tab-content">
            <div className="form-section">
              <h2>Add New User</h2>
              <form onSubmit={handleAddUser} className="user-form">
                <input
                  type="text"
                  placeholder="Name"
                  value={newUser.name}
                  onChange={(e) => setNewUser({ ...newUser, name: e.target.value })}
                  disabled={loading}
                />
                <input
                  type="email"
                  placeholder="Email"
                  value={newUser.email}
                  onChange={(e) => setNewUser({ ...newUser, email: e.target.value })}
                  disabled={loading}
                />
                <select
                  value={newUser.role}
                  onChange={(e) => setNewUser({ ...newUser, role: e.target.value })}
                  disabled={loading}
                >
                  <option value="user">User</option>
                  <option value="admin">Admin</option>
                </select>
                <button type="submit" disabled={loading}>
                  {loading ? 'Adding...' : 'Add User'}
                </button>
              </form>
            </div>

            <div className="users-section">
              <h2>Users ({users.length})</h2>
              <div className="users-grid">
                {users.map((user) => (
                  <div key={user.id} className="user-card">
                    <h3>{user.name}</h3>
                    <p>{user.email}</p>
                    <span className={`role-badge ${user.role}`}>{user.role}</span>
                    <button 
                      className="delete-btn"
                      onClick={() => handleDeleteUser(user.id)}
                    >
                      Delete
                    </button>
                  </div>
                ))}
              </div>
              <button onClick={fetchUsers} className="refresh-btn">
                Refresh Users
              </button>
            </div>
          </div>
        )}

        {/* Products Tab */}
        {activeTab === 'products' && (
          <div className="tab-content">
            <div className="form-section">
              <h2>Add New Product</h2>
              <form onSubmit={handleAddProduct} className="product-form">
                <input
                  type="text"
                  placeholder="Product Name"
                  value={newProduct.name}
                  onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })}
                />
                <input
                  type="number"
                  step="0.01"
                  placeholder="Price"
                  value={newProduct.price}
                  onChange={(e) => setNewProduct({ ...newProduct, price: e.target.value })}
                />
                <input
                  type="text"
                  placeholder="Category"
                  value={newProduct.category}
                  onChange={(e) => setNewProduct({ ...newProduct, category: e.target.value })}
                />
                <input
                  type="number"
                  placeholder="Stock"
                  value={newProduct.stock}
                  onChange={(e) => setNewProduct({ ...newProduct, stock: e.target.value })}
                />
                <textarea
                  placeholder="Description"
                  value={newProduct.description}
                  onChange={(e) => setNewProduct({ ...newProduct, description: e.target.value })}
                />
                <button type="submit" disabled={loading}>
                  {loading ? 'Adding...' : 'Add Product'}
                </button>
              </form>
            </div>

            <div className="products-section">
              <h2>Products ({products.length})</h2>
              <div className="products-grid">
                {products.map((product) => (
                  <div key={product.id} className="product-card">
                    <h3>{product.name}</h3>
                    <p className="price">${product.price}</p>
                    <p className="category">{product.category}</p>
                    <p className={`stock ${product.stock < 20 ? 'low-stock' : ''}`}>
                      Stock: {product.stock}
                    </p>
                    <p className="description">{product.description}</p>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}

        {/* Tasks Tab */}
        {activeTab === 'tasks' && (
          <div className="tab-content">
            <div className="form-section">
              <h2>Add New Task</h2>
              <form onSubmit={handleAddTask} className="task-form">
                <input
                  type="text"
                  placeholder="Task Title"
                  value={newTask.title}
                  onChange={(e) => setNewTask({ ...newTask, title: e.target.value })}
                />
                <select
                  value={newTask.priority}
                  onChange={(e) => setNewTask({ ...newTask, priority: e.target.value })}
                >
                  <option value="low">Low Priority</option>
                  <option value="medium">Medium Priority</option>
                  <option value="high">High Priority</option>
                </select>
                <select
                  value={newTask.assignedTo}
                  onChange={(e) => setNewTask({ ...newTask, assignedTo: e.target.value })}
                >
                  <option value="">Unassigned</option>
                  {users.map(user => (
                    <option key={user.id} value={user.id}>{user.name}</option>
                  ))}
                </select>
                <button type="submit" disabled={loading}>
                  {loading ? 'Adding...' : 'Add Task'}
                </button>
              </form>
            </div>

            <div className="tasks-section">
              <h2>Tasks ({tasks.length})</h2>
              <div className="tasks-list">
                {tasks.map((task) => (
                  <div key={task.id} className={`task-item ${task.completed ? 'completed' : ''}`}>
                    <div className="task-content">
                      <h3>{task.title}</h3>
                      <span className={`priority-badge ${task.priority}`}>{task.priority}</span>
                      {task.assignedTo && (
                        <span className="assigned-to">
                          Assigned to: {users.find(u => u.id === task.assignedTo)?.name || 'Unknown'}
                        </span>
                      )}
                    </div>
                    <button
                      className="toggle-btn"
                      onClick={() => handleToggleTask(task.id, task.completed)}
                    >
                      {task.completed ? '✓' : '○'}
                    </button>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}

        {/* Orders Tab */}
        {activeTab === 'orders' && (
          <div className="tab-content">
            <div className="form-section">
              <h2>Create New Order</h2>
              <form onSubmit={handleAddOrder} className="order-form">
                <select
                  value={newOrder.userId}
                  onChange={(e) => setNewOrder({ ...newOrder, userId: e.target.value })}
                >
                  <option value="">Select User</option>
                  {users.map(user => (
                    <option key={user.id} value={user.id}>{user.name}</option>
                  ))}
                </select>
                <select
                  value={newOrder.productId}
                  onChange={(e) => setNewOrder({ ...newOrder, productId: e.target.value })}
                >
                  <option value="">Select Product</option>
                  {products.map(product => (
                    <option key={product.id} value={product.id}>
                      {product.name} - ${product.price} (Stock: {product.stock})
                    </option>
                  ))}
                </select>
                <input
                  type="number"
                  min="1"
                  placeholder="Quantity"
                  value={newOrder.quantity}
                  onChange={(e) => setNewOrder({ ...newOrder, quantity: parseInt(e.target.value) })}
                />
                <button type="submit" disabled={loading}>
                  {loading ? 'Creating...' : 'Create Order'}
                </button>
              </form>
            </div>

            <div className="orders-section">
              <h2>Orders ({orders.length})</h2>
              <div className="orders-list">
                {orders.map((order) => (
                  <div key={order.id} className="order-item">
                    <h3>Order #{order.id}</h3>
                    <p>User: {users.find(u => u.id === order.userId)?.name || 'Unknown'}</p>
                    <p>Product: {products.find(p => p.id === order.productId)?.name || 'Unknown'}</p>
                    <p>Quantity: {order.quantity}</p>
                    <p>Total: ${order.totalPrice}</p>
                    <span className={`status-badge ${order.status}`}>{order.status}</span>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}

        {/* Search Tab */}
        {activeTab === 'search' && (
          <div className="tab-content">
            <div className="search-section">
              <h2>🔍 Search</h2>
              <div className="search-form">
                <input
                  type="text"
                  placeholder="Search users, products, tasks..."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                  onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
                />
                <button onClick={handleSearch} disabled={loading}>
                  {loading ? 'Searching...' : 'Search'}
                </button>
              </div>
              
              {searchResults && (
                <div className="search-results">
                  <h3>Search Results for "{searchResults.query}" ({searchResults.totalResults} found)</h3>
                  
                  {searchResults.results.users && searchResults.results.users.length > 0 && (
                    <div className="result-category">
                      <h4>Users ({searchResults.results.users.length})</h4>
                      <div className="results-grid">
                        {searchResults.results.users.map(user => (
                          <div key={user.id} className="result-card">
                            <h5>{user.name}</h5>
                            <p>{user.email}</p>
                            <span className={`role-badge ${user.role}`}>{user.role}</span>
                          </div>
                        ))}
                      </div>
                    </div>
                  )}
                  
                  {searchResults.results.products && searchResults.results.products.length > 0 && (
                    <div className="result-category">
                      <h4>Products ({searchResults.results.products.length})</h4>
                      <div className="results-grid">
                        {searchResults.results.products.map(product => (
                          <div key={product.id} className="result-card">
                            <h5>{product.name}</h5>
                            <p>${product.price} - {product.category}</p>
                            <p>Stock: {product.stock}</p>
                          </div>
                        ))}
                      </div>
                    </div>
                  )}
                  
                  {searchResults.results.tasks && searchResults.results.tasks.length > 0 && (
                    <div className="result-category">
                      <h4>Tasks ({searchResults.results.tasks.length})</h4>
                      <div className="results-grid">
                        {searchResults.results.tasks.map(task => (
                          <div key={task.id} className="result-card">
                            <h5>{task.title}</h5>
                            <span className={`priority-badge ${task.priority}`}>{task.priority}</span>
                            <span className={`status ${task.completed ? 'completed' : 'pending'}`}>
                              {task.completed ? 'Completed' : 'Pending'}
                            </span>
                          </div>
                        ))}
                      </div>
                    </div>
                  )}
                  
                  {searchResults.totalResults === 0 && (
                    <p>No results found for "{searchResults.query}"</p>
                  )}
                </div>
              )}
            </div>
          </div>
        )}

        {/* Files & Themes Tab */}
        {activeTab === 'files' && (
          <div className="tab-content">
            {/* Theme Selection */}
            <div className="theme-section">
              <h2>
                {hasImageBackground ? '🎨 Text Overlay Themes' : '🎨 Background Themes'}
              </h2>
              <div className="theme-selector">
                <div className="theme-options">
                  {[
                    { value: 'default', name: 'Default Dark', color: '#282c34' },
                    { value: 'light', name: 'Light Mode', color: '#ffffff' },
                    { value: 'blue', name: 'Ocean Blue', color: '#1e3a8a' },
                    { value: 'green', name: 'Forest Green', color: '#166534' },
                    { value: 'purple', name: 'Royal Purple', color: '#7c3aed' },
                    { value: 'gradient', name: 'Sunset Gradient', color: 'linear-gradient(45deg, #ff6b6b, #ffd93d)' },
                    ...(hasImageBackground ? [] : [{ value: 'image', name: 'Image Background', color: '#333' }])
                  ].map(theme => (
                    <button
                      key={theme.value}
                      className={`theme-option ${backgroundTheme === theme.value ? 'active' : ''}`}
                      onClick={() => handleThemeChange(theme.value)}
                      style={{ 
                        background: theme.color,
                        border: backgroundTheme === theme.value ? '3px solid #61dafb' : '2px solid #ccc'
                      }}
                    >
                      {theme.name === 'Image Background' ? '🖼️ ' : ''}{theme.name}
                    </button>
                  ))}
                </div>
                <p className="theme-info">
                  {hasImageBackground 
                    ? `Current text overlay: ${backgroundTheme}` 
                    : `Current theme: ${backgroundTheme}`
                  }
                </p>
                {hasImageBackground && (
                  <div className="image-mode-info">
                    <p>🖼️ Image background mode active - themes control text overlay styling</p>
                    <button 
                      className="clear-image-btn"
                      onClick={() => {
                        setHasImageBackground(false);
                        handleThemeChange(backgroundTheme);
                      }}
                    >
                      🗑️ Remove Image Background
                    </button>
                  </div>
                )}
              </div>
            </div>

            {/* File Upload Section */}
            <div className="file-upload-section">
              <h2>📁 File Upload</h2>
              <form onSubmit={handleFileSubmit} className="file-upload-form">
                <div className="upload-area">
                  <input
                    id="fileInput"
                    type="file"
                    onChange={handleFileUpload}
                    accept=".jpg,.jpeg,.png,.gif,.txt,.pdf"
                    disabled={loading}
                  />
                  <div className="upload-info">
                    {selectedFile ? (
                      <div className="selected-file">
                        <p><strong>Selected:</strong> {selectedFile.name}</p>
                        <p><strong>Size:</strong> {formatFileSize(selectedFile.size)}</p>
                        <p><strong>Type:</strong> {selectedFile.type}</p>
                        {selectedFile.type.startsWith('image/') && (
                          <p className="image-notice">🖼️ <strong>This image will automatically become your background!</strong></p>
                        )}
                      </div>
                    ) : (
                      <div className="upload-placeholder">
                        <p>📎 Choose a file to upload</p>
                        <p>🖼️ Images (JPEG, PNG, GIF) will automatically become backgrounds</p>
                        <p>📄 Also supports: TXT, PDF (Max 5MB)</p>
                      </div>
                    )}
                  </div>
                </div>

                <div className="theme-for-file">
                  <label>Background theme for this file:</label>
                  <select
                    value={backgroundTheme}
                    onChange={(e) => setBackgroundTheme(e.target.value)}
                    disabled={loading}
                  >
                    <option value="default">Default Dark</option>
                    <option value="light">Light Mode</option>
                    <option value="blue">Ocean Blue</option>
                    <option value="green">Forest Green</option>
                    <option value="purple">Royal Purple</option>
                    <option value="gradient">Sunset Gradient</option>
                    <option value="image">Use as Image Background</option>
                  </select>
                </div>

                <button type="submit" disabled={loading || !selectedFile}>
                  {loading ? 'Uploading...' : 'Upload File'}
                </button>
              </form>
            </div>

            {/* Uploaded Files List */}
            <div className="uploaded-files-section">
              <h2>📋 Uploaded Files ({uploadedFiles.length})</h2>
              {uploadedFiles.length === 0 ? (
                <p className="no-files">No files uploaded yet.</p>
              ) : (
                <div className="files-list">
                  {uploadedFiles.map((file) => (
                    <div key={file.id} className="file-item">
                      <div className="file-info">
                        <div className="file-header">
                          <h4>{file.name}</h4>
                          {file.isImage && (
                            <div className="image-preview">
                              <img src={file.fileURL} alt={file.name} style={{
                                width: '60px',
                                height: '60px',
                                objectFit: 'cover',
                                borderRadius: '8px',
                                border: '2px solid rgba(255,255,255,0.3)'
                              }} />
                            </div>
                          )}
                        </div>
                        <p>Size: {formatFileSize(file.size)}</p>
                        <p>Type: {file.type}</p>
                        <p>Theme: <span className="theme-badge">{file.backgroundTheme}</span></p>
                        <p>Uploaded: {new Date(file.uploadedAt).toLocaleString()}</p>
                      </div>
                      <div className="file-actions">
                        <button
                          className="delete-file-btn"
                          onClick={() => handleDeleteFile(file.id)}
                        >
                          🗑️ Delete
                        </button>
                        <button
                          className="apply-theme-btn"
                          onClick={() => applyFileAsBackground(file)}
                        >
                          {file.isImage && file.backgroundTheme === 'image' ? '🖼️ Set as Background' : '🎨 Apply Theme'}
                        </button>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        )}
      </header>
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
    </div>
  );
}

export default App;
