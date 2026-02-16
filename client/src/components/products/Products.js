import React, { useState } from 'react';
import { toast } from 'react-toastify';
import API from '../../services/api';

function Products({ products, loading, fetchProducts, fetchAnalytics }) {
  const [newProduct, setNewProduct] = useState({ 
    name: '', 
    price: '', 
    category: '', 
    stock: '', 
    description: '' 
  });
  
  const handleAddProduct = async (e) => {
    e.preventDefault();
    console.log('Adding product:', newProduct);
    
    if (!newProduct.name || !newProduct.price || !newProduct.category) {
      console.log('Missing required fields');
      toast.error('Please fill in all required fields: name, price, and category');
      return;
    }

    try {
      console.log('Making API call to:', `/api/products`);
      const result = await API.products.create(newProduct);
      
      console.log('Product created successfully:', result);
      setNewProduct({ name: '', price: '', category: '', stock: '', description: '' });
      fetchProducts();
      fetchAnalytics();
      toast.success('Product added successfully!');
    } catch (error) {
      console.error('Error adding product:', error);
      toast.error('Error adding product: ' + error.message);
    }
  };

  return (
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
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Products;
