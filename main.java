import java.io.IOException;
import java.util.*;



public class main {
    public static void main(String args[]) {
        beginningOfBotGame game= new beginningOfBotGame();
     int[][]battlefield=new int[10][10];
     battlefield=game.emptyFillingField();
     battlefield=game.fillingField();
     game.everythingIsOk(battlefield);
    }
}

abstract class  creatingOfBattlefield {
    static int FIELDSIZE = 10;
    static int AMOUNTOFBOATS = 10;
    int[][] sizeOfBoats = {{1, 4}, {2, 3}, {3, 2}, {4, 1}};

    int[][] emptyFillingField() {
        int[][] battleField = new int[FIELDSIZE][FIELDSIZE];
        for (int posY = 0; posY < FIELDSIZE; posY++) {
            for (int posX = 0; posX < FIELDSIZE; posX++) {
                battleField[posY][posX] = 0;
            }
        }
        return battleField;
    }
    abstract int[][]fillingField();
    abstract boolean checkValidatePlace(int posX, int posY, int typeOfBoat, boolean position, int[][] battleField);
}
 class beginningOfBotGame extends creatingOfBattlefield {
     int[][] fillingField() {
         int typeOfBoat = 3;
         int[][] battleField = emptyFillingField();
         for (int countOfPlacedBoats = 0; countOfPlacedBoats < AMOUNTOFBOATS; countOfPlacedBoats++) {
             typeOfBoat = randomPlacingBoats(typeOfBoat, battleField);
         }
         return battleField;
     }

     int randomPlacingBoats(int typeOfBoat, int[][] battleField) {
         Random random = new Random();
         boolean endOfPlacing = true;
         int boatSize = 0;
         int range = 0;
         int randomPosX, randomPosY;
         boolean position = random.nextBoolean(); // случайная генерация расположения корабля
         while (endOfPlacing) {
             randomPosX = range + (int) (Math.random() * (FIELDSIZE - 1));
             randomPosY = range + (int) (Math.random() * (FIELDSIZE - 1));
//            System.out.println("randomPosX: "+randomPosX+" randomPosY: "+randomPosY);
             if (checkValidatePlace(randomPosX, randomPosY, typeOfBoat + 1, position, battleField)) { // проверка возможности расположения корабля в данных коор-дах
                 if (position) {
                     for (int placeY = 0; placeY < sizeOfBoats[typeOfBoat][0]; placeY++) { // расположение корабля по вертикали
//                        try {
                         battleField[randomPosY][randomPosX] = 1;
//                        } catch (ArrayIndexOutOfBoundsException e){
//                            System.out.println("вылетел на PosX:"+randomPosX+" PosY:"+randomPosY);
//                            System.exit(99);
//                        }
                         randomPosY++;
                     }
                     endOfPlacing = false;
                     sizeOfBoats[typeOfBoat][1] = sizeOfBoats[typeOfBoat][1] - 1;
                 } else {
                     for (int placeX = 0; placeX < sizeOfBoats[typeOfBoat][0]; placeX++) { // расположение корабля по горизонтали
//                    try {
                         battleField[randomPosY][randomPosX] = 1;
//                    } catch (ArrayIndexOutOfBoundsException e){
//                        System.out.println("вылетел на PosX:"+randomPosX+" PosY:"+randomPosY);
//                        System.exit(99);
//                    }
                         randomPosX++;
                     }
                     sizeOfBoats[typeOfBoat][1] = sizeOfBoats[typeOfBoat][1] - 1;
                     endOfPlacing = false;
                 }
             }
         }

         if (sizeOfBoats[typeOfBoat][1] == 0) { // если корабли данного типа закончились, строим корабли другого типа
             typeOfBoat--;
             // everythingIsOk();
             //  System.out.println("types: "+ sizeOfBoats[typeOfBoat][0]);
         }

         return typeOfBoat;
     }
boolean checkValidatePlace(int posX, int posY, int typeOfBoat, boolean position, int[][] battleField) { // проверка возможности расположения корабля
         boolean isValid = false;
         boolean breakable = false;
         int countOfCheck = 2 + typeOfBoat;//снизу, сверху и размер корабля для проверки
         int radiusOfCheck = 2; // для проверки клеток на отсутствие корабля

         if (position) { // вертикальное расположение корабля
             if (FIELDSIZE - posY - typeOfBoat - 1 > 0) {
                 for (int checkPosY = posY - 1; checkPosY < posY + countOfCheck - 1; checkPosY++) {
                     for (int checkPosX = posX - 1; checkPosX < posX + radiusOfCheck; checkPosX++) {
                         if (!((checkPosY >= FIELDSIZE) || (checkPosY <= -1) || (checkPosX <= -1) || (checkPosX >= FIELDSIZE))) {
                             isValid = true;
                             if (battleField[checkPosY][checkPosX] == 1) {
                                 isValid = false;
                                 breakable = true;
                                 break;
                             }
                         }
                     }
                     if (breakable) {
                         break;
                     }
                 }
             }
         } else {
             if (FIELDSIZE - posX - typeOfBoat - 1 > 0) {
                 for (int checkPosX = posX - 1; checkPosX < posX + countOfCheck - 1; checkPosX++) {
                     for (int checkPosY = posY - 1; checkPosY < posY + radiusOfCheck; checkPosY++) {
                         if (!((checkPosY >= FIELDSIZE) || (checkPosY <= -1) || (checkPosX <= -1) || (checkPosX >= FIELDSIZE))) {
                             isValid = true;
                             if (battleField[checkPosY][checkPosX] == 1) {
                                 breakable = true;
                                 isValid = false;
                                 break;
                             }
                         }
                     }
                     if (breakable) {
                         break;
                     }
                 }
             }
         }

         return isValid;
     }

     void everythingIsOk(int[][] battleField) {
         for (int posY = 0; posY < FIELDSIZE; posY++) {
             for (int posX = 0; posX < FIELDSIZE; posX++) {
                 System.out.print(battleField[posY][posX]);
             }
             System.out.println("");
         }
     }
 }
