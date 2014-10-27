package Maxflow;

public class Main {

    private static int[][] input;

    public static void main(String[] args) {

        int[][] input1 = new int[5][2];
        input1[0][0] = 4;
        input1[0][1] = 0;

        input1[1][0] = 1;
        input1[1][1] = 2;

        input1[2][0] = 2;
        input1[2][1] = 1;

        input1[3][0] = 2;
        input1[3][1] = 2;

        input1[4][0] = 3;
        input1[4][1] = 3;


        int[][] input2 = new int[10][2];
        input2[0][0] = 9;
        input2[0][1] = 0;

        input2[1][0] = 1;
        input2[1][1] = 2;

        input2[2][0] = 1;
        input2[2][1] = 3;

        input2[3][0] = 3;
        input2[3][1] = 1;

        input2[4][0] = 3;
        input2[4][1] = 4;

        input2[5][0] = 4;
        input2[5][1] = 3;

        input2[6][0] = 5;
        input2[6][1] = 3;

        input2[7][0] = 5;
        input2[7][1] = 4;

        input2[8][0] = 6;
        input2[8][1] = 6;

        //Extra
        input2[9][0] = 2;
        input2[9][1] = 5;

        MaxFlowCounter test1 = new MaxFlowCounter(input1);
        MaxFlowCounter test2 = new MaxFlowCounter(input2);


    }

}
