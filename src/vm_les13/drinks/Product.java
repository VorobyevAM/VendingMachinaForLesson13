package vm_les13.drinks;

import vm_les13.drinks.interfaceDrink.DrinkType;
import vm_les13.exception.MyException;

/**
 * Класс-обертка "Информация по товару"
 *
 * Добавляет к типу товара дополнительное поле: количество
 */
public class Product {
    private final DrinkType drinkType;
    private int quantity;

    public Product(DrinkType drinkType, int quantity) {
        this.drinkType = drinkType;
        this.quantity = quantity;
    }

    /**
     * Изъятие напитка из хранилища
     * Меняет количество товара в хранлище
     *
     * @return тип напитка
     */
    public DrinkType take() throws MyException {

        if (quantity==0){
            throw new MyException("Товар закончился. Выберите пожалуйста другой!");
        }

        quantity--;
        return drinkType;
    }

    public String getName() {
        return drinkType.getName();
    }
    public double getPrice() {
        return drinkType.getPrice();
    }
}
