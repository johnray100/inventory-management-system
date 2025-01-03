package jay.inventory_management_system.Product.Service;

import jay.inventory_management_system.Category.Model.Category;
import jay.inventory_management_system.Category.Repository.CategoryRepository;
import jay.inventory_management_system.Product.Model.Product;
import jay.inventory_management_system.Product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProductsByName(String keyword) {
        return productRepository.findByProductNameContaining(keyword);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }


    // Create a new product
    public Product createProduct(Product product) {
        // Validate category exists
        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + product.getCategory().getId()));

        product.setCategory(category); // Link product to the category
        return productRepository.save(product);
    }

    // Update an existing product
    public Product updateProduct(Long id, Product updatedProduct) {
        // Find existing product
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        // Update fields
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDescription(updatedProduct.getDescription());

        // Validate and update the category
        Category category = categoryRepository.findById(updatedProduct.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + updatedProduct.getCategory().getId()));
        existingProduct.setCategory(category);

        return productRepository.save(existingProduct);
    }

    // Delete a product by ID
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
