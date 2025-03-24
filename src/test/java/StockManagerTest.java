import com.tbrasseur.InsufficientStockException;
import com.tbrasseur.StockManager;
import com.tbrasseur.UnknownProductException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockManagerTest {

    private static StockManager stockManager;
    @BeforeAll
    public static void setUp(){
        stockManager = new StockManager();
        stockManager.setQuantity("PRODUCT1", 10);
        stockManager.setQuantity("PRODUCT2", 0);
        stockManager.setQuantity("PRODUCT3", 5);
        stockManager.setQuantity("PRODUCT4", 1);
    }

    @BeforeEach
    public void setQuantities(){
        stockManager.setQuantity("PRODUCT1", 10);
        stockManager.setQuantity("PRODUCT2", 0);
        stockManager.setQuantity("PRODUCT3", 5);
        stockManager.setQuantity("PRODUCT4", 1);
    }

    @Test
    void testHasStock() throws UnknownProductException {
        assertTrue(stockManager.hasStock("PRODUCT1"));
    }

    @Test
    void testHasNoStock() throws UnknownProductException {
        assertFalse(stockManager.hasStock("PRODUCT2"));
    }

    @Test
    void testContainProduct(){ 
        assertTrue(stockManager.containProduct("PRODUCT1"));
        assertFalse(stockManager.containProduct("PRODUCT9"));
    }

    @Test
    void testGetQuantity() throws UnknownProductException {
        assertEquals(10,stockManager.getQuantity("PRODUCT1"));
        assertEquals(0,stockManager.getQuantity("PRODUCT2"));
        assertEquals(5,stockManager.getQuantity("PRODUCT3"));
        assertEquals(1,stockManager.getQuantity("PRODUCT4"));
    }
    @Test
    void testUnknownProduct(){
        assertThrows(UnknownProductException.class,()-> {
            stockManager.getQuantity("UNKNOWN_PRODUCT");
        });
    }

    @Test
    void testDestock() throws UnknownProductException, InsufficientStockException {
        stockManager.destock("PRODUCT1");
        assertEquals(9,stockManager.getQuantity("PRODUCT1"));
        stockManager.destock("PRODUCT3");
        assertEquals(4,stockManager.getQuantity("PRODUCT3"));
        stockManager.destock("PRODUCT4");
        assertEquals(0,stockManager.getQuantity("PRODUCT4"));
    }

    @Test
    void testDestockInsufficientQuantity(){
        assertThrows(InsufficientStockException.class,()->{
            stockManager.destock("PRODUCT2");
        });
    }

    @Test
    void testNDestock() throws InsufficientStockException, UnknownProductException {
        stockManager.destock("PRODUCT1",5);
        assertEquals(5,stockManager.getQuantity("PRODUCT1"));
        stockManager.destock("PRODUCT3",3);
        assertEquals(2,stockManager.getQuantity("PRODUCT3"));
        stockManager.destock("PRODUCT4",1);
        assertEquals(0,stockManager.getQuantity("PRODUCT4"));
    }

    @Test
    void testNDestockInsufficientQuantity(){
        assertThrows(InsufficientStockException.class,()->{
            stockManager.destock("PRODUCT1",11);
        });
        assertThrows(InsufficientStockException.class,()->{
            stockManager.destock("PRODUCT2",1);
        });

    }
    @Test
    void testRestock() throws UnknownProductException {
        stockManager.restock("PRODUCT1");
        assertEquals(11,stockManager.getQuantity("PRODUCT1"));

    }

    @Test
    void testNRestock() throws UnknownProductException {
        stockManager.restock("PRODUCT1",5);
        assertEquals(15,stockManager.getQuantity("PRODUCT1"));
        stockManager.restock("PRODUCT3",3);
        assertEquals(8,stockManager.getQuantity("PRODUCT3"));
        stockManager.restock("PRODUCT4",1);
        assertEquals(2,stockManager.getQuantity("PRODUCT4"));
        stockManager.restock("PRODUCT5",1);
        assertEquals(1,stockManager.getQuantity("PRODUCT5"));
    }

    @Test
    void testDeleteProduct() throws UnknownProductException {
        stockManager.delete("PRODUCT1");
        assertFalse(stockManager.containProduct("PRODUCT1"));
    }
}
