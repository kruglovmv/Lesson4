package com.company;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class Lesson4 {
    public static char[][] map;
    public static final int SIZE = 5;
    public static final int DOTS_TO_WIN = 3;
    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static int xPosition = 0;
    public static int yPosition = 0;

    public static void main(String[] args) {
        initMap();
        showMap();
        boolean flag = false;
        do {
            humanTry();
            showMap();
            if (checkWin(DOT_X)) {
                System.out.println("Вы выиграли!!!");
                flag = true;
                break;
            }
            if(!mapIsFull()){
                computerTry();
                showMap();
            }
            if (checkWin(DOT_O)) {
                System.out.println("Вы проиграли!!!");
                flag = true;
                break;
            }
        }while(!mapIsFull());
        if(!flag) System.out.println("Игра закончилась ничьёй!!!");
    }

    private static boolean mapIsFull() {
        for (char [] row:map) {
            for (int i = 0; i < SIZE; i++) {
                if(row[i]==DOT_EMPTY) return false;
            }
        }
        return true;
    }

    private static boolean checkWin(char symbol) {
        StringBuilder equalsString = new StringBuilder();
        equalsString.append(String.valueOf(symbol).repeat(DOTS_TO_WIN));
        for (int i = 0; i < SIZE; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < SIZE; j++) {
                str.append(map[i][j]);
            }
            if (str.toString().contains(equalsString.toString())) return true;
        }
        for (int i = 0; i < SIZE; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < SIZE; j++) {
                str.append(map[j][i]);
            }
            if (str.toString().contains(equalsString.toString())) return true;
        }
        for (int j = 0; j < (SIZE-DOTS_TO_WIN)+1; j++) {
            StringBuilder str = new StringBuilder();
            StringBuilder str2 = new StringBuilder();
            for (int i = 0; i < SIZE-j; i++) {
                str.append(map[i][i+j]);
                str2.append(map[i+j][i]);
            }if (str.toString().contains(equalsString.toString())||str2.toString().contains(equalsString.toString())) return true;
        }

        for (int j = 0; j < (SIZE-DOTS_TO_WIN)+1; j++) {
            StringBuilder str = new StringBuilder();
            StringBuilder str2 = new StringBuilder();
            for (int i = 0; i <SIZE-j ; i++) {
                str.append(map[i][(SIZE - 1) - i -j]);
                str2.append(map[i +j][(SIZE - 1) - i]);
            }
            if (str.toString().contains(equalsString.toString())||str2.toString().contains(equalsString.toString())) return true;
        }
        return false;
    }
    private static void computerTry() {
        System.out.println("Ход компьютера:");
        int[] position = {0,0};
        boolean flagTry = false;
        flagTry = (SIZE-DOTS_TO_WIN==0)? checkNextTry(DOTS_TO_WIN-2, position): checkNextTry(DOTS_TO_WIN-1, position);
        if(!flagTry)
            do {
                position[0] = new Random().nextInt(SIZE);
                position[1] = new Random().nextInt(SIZE);
            } while (!checkTry(position[0] , position[1] ));
        map[position[0]][position[1]]=DOT_O;
    }

    private static boolean checkNextTry(int counter, int[] position) {
        StringBuilder equalsString = new StringBuilder();
        equalsString.append(String.valueOf(DOT_X).repeat(counter));
        StringBuilder str = new StringBuilder();
        for (int j = 0; j < SIZE; j++) {
            str.append(map[xPosition][j]);
        }
        if(str.indexOf(equalsString.toString())!=-1) {
            if (checkTry(xPosition, str.indexOf(equalsString.toString()) - 1)) {
                position[0] = xPosition;
                position[1] = str.indexOf(equalsString.toString()) - 1;
                return true;
            } else if (checkTry(xPosition, str.indexOf(equalsString.toString()) + counter)) {
                position[0] = xPosition;
                position[1] = str.indexOf(equalsString.toString()) + counter;
                return true;
            }
        }
        str.setLength(0);
        for (int j = 0; j < SIZE; j++) {
            str.append(map[j][yPosition]);
        }
        if(str.indexOf(equalsString.toString())!=-1) {
            if(checkTry(str.indexOf(equalsString.toString())-1, yPosition)){
                position[0] = str.indexOf(equalsString.toString())-1;
                position[1] = yPosition;
                return true;
            } else if (checkTry(str.indexOf(equalsString.toString())+counter, yPosition)) {
                position[0] = str.indexOf(equalsString.toString())+counter;
                position[1] = yPosition;
                return true;
            }
        }
        str.setLength(0);
        if(xPosition>yPosition){
            int i = xPosition-yPosition;
            int j = 0;
            while (i<SIZE){
                str.append(map[i][j]);
                i++;
                j++;
            }
            if(str.indexOf(equalsString.toString())!=-1) {
                if(checkTry(xPosition-yPosition + str.indexOf(equalsString.toString())-1, str.indexOf(equalsString.toString())-1)){
                    position[0] = xPosition-yPosition + str.indexOf(equalsString.toString())-1;
                    position[1] = str.indexOf(equalsString.toString())-1;
                    return true;
                } else if (checkTry(xPosition-yPosition + str.indexOf(equalsString.toString())+counter, str.indexOf(equalsString.toString())+counter)) {
                    position[0] = xPosition-yPosition +str.indexOf(equalsString.toString())+counter;
                    position[1] = str.indexOf(equalsString.toString())+counter;
                    return true;
                }
            }
        } else { int i = 0;
            int j = yPosition-xPosition;
            while (j<SIZE){
                str.append(map[i][j]);
                i++;
                j++;
            }
            if(str.indexOf(equalsString.toString())!=-1) {
                if(checkTry(str.indexOf(equalsString.toString())-1, yPosition-xPosition+str.indexOf(equalsString.toString())-1)){
                    position[0] = str.indexOf(equalsString.toString())-1;
                    position[1] = yPosition-xPosition+str.indexOf(equalsString.toString())-1;
                    return true;
                } else if (checkTry(str.indexOf(equalsString.toString())+counter, yPosition-xPosition+str.indexOf(equalsString.toString())+counter)) {
                    position[0] = str.indexOf(equalsString.toString())+counter;
                    position[1] = yPosition-xPosition+str.indexOf(equalsString.toString())+counter;
                    return true;
                }
            }
        }
        str.setLength(0);
        if(xPosition+yPosition<SIZE-1){
            int i = xPosition+yPosition;
            int j = 0;
            while (i>=0){
                str.append(map[i][j]);
                i--;
                j++;
            }
            if(str.indexOf(equalsString.toString())!=-1) {
                if(checkTry(xPosition+yPosition - str.indexOf(equalsString.toString())-1, str.indexOf(equalsString.toString())-1)){
                    position[0] = xPosition+yPosition - str.indexOf(equalsString.toString())-1;
                    position[1] = str.indexOf(equalsString.toString())-1;
                    return true;
                } else if (checkTry(xPosition+yPosition - str.indexOf(equalsString.toString())+counter, str.indexOf(equalsString.toString())+counter)) {
                    position[0] = xPosition+yPosition -str.indexOf(equalsString.toString())+counter;
                    position[1] = str.indexOf(equalsString.toString())+counter;
                    return true;
                }
            }
        } else { int i = SIZE-1;
            int j = yPosition+xPosition - (SIZE-1);
            while (j<SIZE){
                str.append(map[i][j]);
                i--;
                j++;
            }
            if(str.indexOf(equalsString.toString())!=-1) {
                if(checkTry(SIZE-1 - str.indexOf(equalsString.toString())-1, yPosition+xPosition - (SIZE-1)+str.indexOf(equalsString.toString())-1)){
                    position[0] = SIZE-1 - str.indexOf(equalsString.toString())-1;
                    position[1] = yPosition+xPosition - (SIZE-1)+str.indexOf(equalsString.toString())-1;
                    return true;
                } else if (checkTry(SIZE-1 -str.indexOf(equalsString.toString())+counter, yPosition+xPosition - (SIZE-1)+str.indexOf(equalsString.toString())+counter)) {
                    position[0] = SIZE-1 -str.indexOf(equalsString.toString())+counter;
                    position[1] = yPosition+xPosition - (SIZE-1)+str.indexOf(equalsString.toString())+counter;
                    return true;
                }
            }
        }
        return false;
    }


    private static void humanTry() {
        System.out.println("Введите координаты хода:");
        Scanner sc = new Scanner(System.in);
        do{
            System.out.print("Строка- ");
            xPosition =sc.nextInt()-1;
            System.out.print("Столбец - ");
            yPosition = sc.nextInt()-1;
        }while(!checkTry(xPosition, yPosition));
        map[xPosition][yPosition]=DOT_X;
    }

    private static boolean checkTry(int x, int y) {
        if ((x>=0) && (x<SIZE)&&(y>=0)&&(y<SIZE)&&map[x][y]==DOT_EMPTY) return true;
        return false;
    }

    private static void showMap() {
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print("[ ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.print("]");
            System.out.println();
        }
        System.out.println();
    }

    private static void initMap() {
        map = new char[SIZE][SIZE];
        for (char []row: map) {
            Arrays.fill(row, DOT_EMPTY);

        }
    }
}

