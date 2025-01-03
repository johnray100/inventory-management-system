package jay.inventory_management_system.Transaction.Controller;

import jay.inventory_management_system.Product.Model.Product;
import jay.inventory_management_system.Product.Service.ProductService;
import jay.inventory_management_system.Transaction.Model.Transaction;
import jay.inventory_management_system.Transaction.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ProductService productService;

    @GetMapping("/transactions")
    public String listTransaction(Model model){
        model.addAttribute("activePage", "transaction");
        model.addAttribute("transactions", transactionService.getAllTransaction());
        return "views/backend/transaction/index";
    }

    @GetMapping("/add-transaction")
    public String showFormTransaction(Model model, Transaction transaction){
        model.addAttribute("activePage", "transaction");
        model.addAttribute("transaction", transaction);
        model.addAttribute("products", productService.getAllProducts());
        return "views/backend/transaction/create";
    }

    @PostMapping("/create-transaction")
    public String createTransaction(Transaction transaction, Model model){
        model.addAttribute("activePage", "transaction");
        transactionService.saveTransaction(transaction);
        return "redirect:/transactions";
    }

    @GetMapping("/delete-transaction/{id}")
    public String deleteTransactionById(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return "redirect:/transactions"; // Redirect to product list
    }

    @GetMapping("/update-transaction/{id}")
    public String showEditTransactionForm(@PathVariable(value = "id") Long id, Model model){
        // Fetch the transaction by ID
        Transaction transaction = transactionService.getTransactionById(id);

        // Fetch all products for the dropdown
        List<Product> products = productService.getAllProducts();

        // Add attributes to the model
        model.addAttribute("transaction", transaction);
        model.addAttribute("activePage", "transaction");
        model.addAttribute("products", products);

        // Return the view
        return "views/backend/transaction/update";
    }
}
