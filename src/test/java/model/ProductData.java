package model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

/**
 * Hold Product information that is used among tests.
 */
public class ProductData {
    private String name;
    private int qty;
    private float price;

    public ProductData(String name, int qty, float price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getQty() {
        return qty;
    }

    public String getPriceStr() {
        DecimalFormatSymbols separators = new DecimalFormatSymbols();
        separators.setDecimalSeparator(',');
        return new DecimalFormat("#0.00", separators).format(price);
    }
    
    public float getPrice() {
        return price;
    }
    /**
     * @return New Product object with random name, quantity and price values.
     */
    public static ProductData generate() {
        Random random = new Random();
        return new ProductData(
                "New Product " + System.currentTimeMillis() + random.nextInt(100),
                random.nextInt(100) + 1,
                (float) Math.round(random.nextInt(100_00) + 1) / 100);
    }

	@Override
	public String toString() {
		return "ProductData [name=" + name + ", qty=" + qty + ", price=" + price + "]";
	}
    
    
}
