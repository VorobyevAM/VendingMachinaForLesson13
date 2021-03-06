import org.apache.log4j.Logger;
import vm_les13.VendingMachine;
import vm_les13.drinks.interfaceDrink.DrinkType;
import vm_les13.exception.MyException;


import java.util.Scanner;



public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private static VendingMachine vm = new VendingMachine();

    public static void main(String[] args) {

        System.setErr(System.out);

        LOG.info("START SESSION");

        System.out.println("Наши напитки: ");
        for (String line : vm.getDrinkTypes()) {
            System.out.println(line);
        }

        Scanner scan = new Scanner(System.in);
        printHelp();
        while (scan.hasNext()) {
            String command = scan.next();

            LOG.info("The user selected an action :" + command);

            switch (command) {
                case "add": {
                    int money = scan.nextInt();
                    processAddMoney(money);
                    break;
                }
                case "get": {
                    int key = scan.nextInt();
                    processGetDrink(key);
                    break;
                }
                case "end": {
                    processEnd();
                    LOG.info("END SESSION\n");
                    return;
                }
                default:
                    System.out.println("Команда не определена");
                    printHelp();
            }
            scan.nextLine();
        }
    }

    /**
     * обработка добавления денег в автомат
     * @param money - сумма
     */
    private static void processAddMoney(int money) {
        try {
            System.out.println("Текущий баланс: " + vm.addMoney(money));
        } catch (MyException e) {
            System.out.println("Заберите вашу купюру. Эта купюра либо сильно мятая, либо поддельная: " + money);
            LOG.error("INCORRECT MONEY", e);

            e.printStackTrace();

            printHelp();
            vm.getMoney();
        }
    }

    /**
     * обработка получения напитка
     * @param key - код напитка в автомате
     */
    private static void processGetDrink(int key) {

        DrinkType drinkType = null;
        try {
            drinkType = vm.giveMeADrink(key);
        } catch (MyException e) {
            LOG.error("NOT ENOUGH MONEY. DEPOSIT = " + vm.getMoney(), e);
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e){
            LOG.error("NO DRINK NUMBER IN THE LIST OF DRINKS" + key, e);
            e.printStackTrace();
        }
        if (drinkType != null) {
            System.out.println("Ммм! " + drinkType.getName() + "!");
            vm.getMoney();
        } else {
            LOG.error("ERROR WITH ISSUING A DRINK");
            System.out.println("Напиток почему-то не получен...");

            System.out.println("Наши напитки: ");
            for (String line : vm.getDrinkTypes()) {
                System.out.println(line);
            }

        }
    }

    /**
     * обработка получения сдачи
     */
    private static void processEnd() {
        System.out.println("Ваша сдача: " + vm.getChange());
    }

    /**
     * выводит подсказку по доступным командам
     */
    private static void printHelp() {
        System.out.println( "Введите 'add <количество>' для добавления купюр" );
        System.out.println( "Введите 'get <код напитка>' для получения напитка" );
        System.out.println( "Введите 'end' для получения сдачи" );
    }
}