import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class App {
    public static List<Product> products = null;

    public static List<Product> loadProducts() {
        List<Product> list = new ArrayList<>();
        File file = new File("save.txt");
        try {
            Scanner scanner = new Scanner(file);
            String autoIdFile = scanner.nextLine();
            String[] s1 = autoIdFile.split(" ");
            Product.autoId = Integer.parseInt(s1[1]);

            /*System.out.println(Product.autoId);
            System.out.println(autoIdFile);*/

            String numberProductData = scanner.nextLine();
            String[] s2 = numberProductData.split(" ");
            int n = Integer.parseInt(s2[1]);
            /*System.out.println(n);
            System.out.println(numberProductData);*/
            //List<String> data = new ArrayList<>();
            /*for (int i = 0; i < n; i++) {
                String line = scanner.nextLine();
                int id = Integer.parseInt(line);
                String name = scanner.nextLine();
                line = scanner.nextLine();
                double price = Double.parseDouble(line);
                Product newProduct = new Product(id, name, price);
                list.add(newProduct);
                //System.out.println(newProduct);
            }*/

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                int id = Integer.parseInt(line);
                String name = scanner.nextLine();
                line = scanner.nextLine();
                double price = Double.parseDouble(line);
                Product newProduct = new Product(id, name, price);
                list.add(newProduct);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println(list.size());
        return list;
    }
    public static void saveProducts() {
        int miss = 0;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).id != i + 1) {
                miss = i + 1;
                break;
            }
        }
        try {
            FileWriter writer = new FileWriter("save.txt");
            writer.write("autoId: " + Product.autoId + "\n");
            writer.write("products_no: " + miss + "\n");
            for (Product product : products) {
                writer.write(product.id + "\n" + product.name + "\n" + product.price + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> sortByPrice() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if(o1.price == o2.price) {
                    return 0;
                }
                return o1.price < o2.price ? -1 : 1;
            }
        });
        return products;
    };

    public static List<Product> sortByNames() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        return products;
    }

    public static List<Product> searchProductByName(String name) {
        List<Product> result = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).name.contains(name)) {
                result.add(products.get(i));
            }
        }
        return result;
    }

    public static void printProduct(List<Product> list) {
        list.forEach(element -> {
            System.out.println(element);
        });
    }

    public static void addProduct(List<Product> list, Product product) {
        list.add(product);
        Product.autoId++;
    }

    public static void editProduct(List<Product> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id == id) {
                Scanner edit = new Scanner(System.in);
                System.out.print("New name product: ");
                String newName = edit.nextLine();
                list.get(i).name = newName;
                System.out.print("New Price product: ");
                String newPrice = edit.nextLine();
                double valuePrice = Double.parseDouble(newPrice);
                list.get(i).price = valuePrice;
                return ;
            }
        }
        System.out.println("Product not found");
    }

    public static void deleteProduct(List<Product> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id == id) {
                list.remove(i);
                return;
            }
        }
        System.out.println("Product not found");
    }

    public static void main(String[] args) {

        System.out.println(Product.autoId);
        products = loadProducts();
        System.out.println(products.size());
        System.out.println(Product.autoId);
        printProduct(products);
        saveProducts();
        Scanner input = new Scanner(System.in);
        boolean running = true;
        while (running) {
            int option = Integer.parseInt(input.nextLine());
            switch (option) {
                case 1:
                    // add product
                    System.out.print("Product name : ");
                    String name = input.nextLine();
                    System.out.print("Product price : ");
                    double price = Double.parseDouble(input.nextLine());
                    addProduct(products, new Product(Product.autoId, name, price));
                    printProduct(products);
                    break;
                case 2:
                    printProduct(products);
                    break;
                case 3:
                    System.out.print("Id edit : ");
                    int idEdit = Integer.parseInt(input.nextLine());
                    editProduct(products, idEdit);
                    break;
                case 4:
                    System.out.print("Id delete : ");
                    int idDelete = Integer.parseInt(input.nextLine());
                    deleteProduct(products, idDelete);
                    printProduct(products);
                    break;
                case 5:
                    System.out.print("Name search : ");
                    String key = input.nextLine();
                    printProduct(searchProductByName(key));
                    break;
                case 6:
                    products = sortByPrice();
                    printProduct(products);
                    break;
                case 7:
                    products = sortByNames();
                    printProduct(products);
                    break;
                case 0:
                    saveProducts();
                    running = false;
                    break;

                default:
                    saveProducts();
                    break;
            }
        }

    }

}
