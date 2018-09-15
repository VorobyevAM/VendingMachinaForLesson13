package vm_les13;

import org.apache.log4j.Logger;
import vm_les13.drinks.ColdDrinkType;
import vm_les13.drinks.interfaceDrink.DrinkType;
import vm_les13.drinks.HotDrinkType;
import vm_les13.drinks.Product;
import vm_les13.exception.MyException;



/**
 * Торговый автомат
 */
public class VendingMachine {

    private static final Logger LOG = Logger.getLogger(VendingMachine.class.getName());

    private double money = 0;
    private Product[] drinks = new Product[]{
            new Product(ColdDrinkType.COCA, 10),
            new Product(ColdDrinkType.PEPSI, 10),
            new Product(ColdDrinkType.SPRITE, 1),
            new Product(ColdDrinkType.FANTA, 3),
            new Product(HotDrinkType.GREEN_TEA, 100),
            new Product(HotDrinkType.BLACK_TEA, 100),
            new Product(HotDrinkType.AMERICANO, 100),
            new Product(HotDrinkType.CAPPUCINO, 100)
    };

    /**
     * Добавление купюр в автомат
     *
     * @param money - сумма внесенных купюр
     * @return текущий остаток
     */
    public double addMoney(double money) throws MyException {

        double stifle = Math.random();//Замятие купюры
        if (stifle<0.2){
            throw new MyException("Купюра " + money + " не подходит");
        }

        this.money += money;
        LOG.info("ADD MONEY IN THE VENDING MACHINA = " + money);
        return this.money;
    }

    /**
     * Получает из автомата выбранный напиток
     *
     * В методе есть проверка корректности кода напитка, проверка остатков данного продукта
     * и достаточно ли денег чтобы его купить. В случае ошибок выбрасывать соответствующее исключение
     *
     * @param key код продукта
     * @return напиток;
     */
    public DrinkType giveMeADrink(int key) throws MyException {
        if (!isKeyValid(key)) {
            LOG.info("CLIENT CHOOSE DRINK NUMBER " + key);
            if (true){
                throw new ArrayIndexOutOfBoundsException("Такого товара нет!");

            }
            // Неправильный код товара - товар не возвращается
            return null;
        }

        Product selected = drinks[key];
        if (!isMoneyEnough(selected)) {
            // Нехватает денег - товар не возвращается
            if (true) {
                throw new MyException("Не хватает денег!");
            }
            return null;
        }

        DrinkType drink = selected.take();
        money -= drink.getPrice();
        return drink;
    }

    /**
     * Формирует список товаров
     *
     * @return строки с информацией о товаре
     */
    public String[] getDrinkTypes() {
        String[] result = new String[drinks.length];
        for (int i = 0; i < drinks.length; i++) {
            result[i] = String.format("%d - %12s: %6.2f руб", i, drinks[i].getName(),drinks[i].getPrice());
        }
        return result;
    }

    /**
     * Проверка что хватает денег на выбранный напиток
     *
     * @param selected - выбранный напиток
     * @return true если денег хватает, иначе - false
     */
    private boolean isMoneyEnough(Product selected) {
        return money >= selected.getPrice();
    }

    /**
     * Проверка что выбрали существующий тип напитка
     * Не проверяет остаток, только границы массива
     *
     * @param key номер напитка
     * @return true если есть напиток с таким номером, иначе false
     */
    private boolean isKeyValid(int key) {
        return key >=0 && key < drinks.length;
    }

    /**
     * Выдать сдачу.
     * Количество введеных купюр обнуляется
     *
     * @return остаток
     */
    public double getChange() {
        double money = this.money;
        this.money = 0;
        return money;
    }

    public double getMoney() {
        System.out.println("Текущий баланс: " + money);
        return money;
    }
}
