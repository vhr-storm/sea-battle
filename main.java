


import java.io.*;
import java.util.*;



public class Main {
    public static void main(String args[]) {
     beginningOfGame game= new beginningOfGame();
     game.emptyFillingField();
     game.fillingField();
     game.everythingIsOk();
    }
}

class beginningOfGame{
    static int FIELDSIZE = 10;
    static int AMOUNTOFBOATS=10;
    int[][] battleField = new int[FIELDSIZE][FIELDSIZE];
    int[][] sizeOfBoats = {{1,4},{2,3},{3,2},{4,1}};
    
    void emptyFillingField(){
        for(int posY = 0 ; posY< FIELDSIZE; posY++){
            for(int posX=0; posX < FIELDSIZE; posX++){
                battleField[posY][posX]=0;
            }
        }
        
        
    }
    
    void fillingField(){
        int typeOfBoat=0;
        for (int countOfPlacedBoats= 0; countOfPlacedBoats < AMOUNTOFBOATS-1; countOfPlacedBoats++){
            typeOfBoat=randomPlacingBoats(typeOfBoat);
        }
    }
    
    int randomPlacingBoats( int typeOfBoat){
        Random random = new Random();
        int boatSize=0;
        int range=0;
        int randomPosX,randomPosY;
        boolean position=random.nextBoolean(); // случайная генерация расположения корабля
        while(true){
            randomPosX=range+(int)(Math.random()*(FIELDSIZE-1));
            randomPosY=range+(int)(Math.random()*(FIELDSIZE-1));
            // System.out.println("randomPosX: "+randomPosX+" randomPosY: "+randomPosY);
            if(checkValidatePlace(randomPosX,randomPosY,typeOfBoat+1,position)){ // проверка возможности расположения корабля в данных коор-дах
                if(position){
                    for(int placeY=0; placeY < sizeOfBoats[typeOfBoat][0]; placeY++){ // расположение корабля по вертикали
                    battleField[randomPosY][randomPosX]=1;
                    randomPosY++;
                }
               sizeOfBoats[typeOfBoat][1]=sizeOfBoats[typeOfBoat][1]-1;
            //   System.out.println("count: "+ sizeOfBoats[typeOfBoat][1]);
               break;
                }
                else {
                    for(int placeX=0; placeX < sizeOfBoats[typeOfBoat][0]; placeX++){ // расположение корабля по горизонтали
                    battleField[randomPosY][randomPosX]=1;
                    randomPosX++;
                }
               sizeOfBoats[typeOfBoat][1]=sizeOfBoats[typeOfBoat][1]-1;
                // System.out.println("count: "+ sizeOfBoats[typeOfBoat][1]);
               break;
                }
            }
        }
            
        if(sizeOfBoats[typeOfBoat][1]==0){ // если корабли данного типа закончились, строим корабли другого типа
            typeOfBoat++;
            // everythingIsOk();
            //  System.out.println("types: "+ sizeOfBoats[typeOfBoat][0]);
        }
        return typeOfBoat;
    }
    
    boolean checkValidatePlace(int posX,int posY,int typeOfBoat, boolean position){ // проверка возможности расположения корабля
        boolean isValid=true;
        int countOfCheck=2+typeOfBoat;//снизу, сверху и размер корабля для проверки
        int radiusOfCheck = 3; // для проверки клеток на отсутствие корабля
        if(position){ // вертикальное расположение корабля
            if(FIELDSIZE-posY-typeOfBoat-1>0){
                for(int checkPosY=posY;checkPosY<posY+countOfCheck;checkPosY++){
                    for(int checkPosX=posX;checkPosX<posX+radiusOfCheck;checkPosX++){
                        if(checkPosX>= FIELDSIZE){
                            continue;
                        }else if (battleField[checkPosY][checkPosX]==1){
                            isValid=false;
                        }
                    }
                }
            }
        }else{
            if(FIELDSIZE-posX-typeOfBoat-1>0){
                for(int checkPosX=posX;checkPosX<posX+countOfCheck;checkPosX++){
                    for(int checkPosY=posY;checkPosY<posY+radiusOfCheck;checkPosY++){
                        if(checkPosY>= FIELDSIZE){
                            continue;
                        }else if (battleField[checkPosY][checkPosX]==1){
                            isValid=false;
                        }
                    }
                }
            }
        }
        
        return isValid;
    }
    
    void everythingIsOk(){
         for(int posY = 0 ; posY< FIELDSIZE; posY++){
             for(int posX=0; posX < FIELDSIZE; posX++){
                 System.out.print(battleField[posY][posX]);
             }
             System.out.println("");
         }
    }
}
