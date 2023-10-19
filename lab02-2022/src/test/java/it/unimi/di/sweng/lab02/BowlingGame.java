package it.unimi.di.sweng.lab02;

public class BowlingGame implements Bowling{
    private final int[] frames;
    private int index;
    public BowlingGame(){
        frames = new int[21];
        index = 0;
    }
    @Override
    public void roll(int pins) {
        frames[index] = pins;
        if(index<18 && pins == 10 && index % 2 == 0) index++;
        index++;
    }

    @Override
    public int score() {
        int sum = 0;
        int bonus = 0;

        for (int i = 0; i < 20; i++) {
            int score = frames[i];
            // strike
            if(i%2 == 0 && score == 10){
                // strike followed not strike
                if(i+2<19 && frames[i+2] != 10){
                    //System.out.println("Strike Followed by not strike");
                    bonus += frames[i+2] + frames[i+3];
                }

                // strike followed by strike
                if(i+2<19 && frames[i+2] == 10){
                    //System.out.println("Strike Followed by Strike "+i);
                    bonus += frames[i+2] + frames[i+4];
                }

                // last frame strike
                if(i==18){
                    //System.out.println("Last frame strike");
                    sum += score + frames[i+1] + frames[i+2];
                    break;
                }

            }

            // spare
            if(i+1 < 19 && i%2 != 0 && score != 0 && score + frames[i-1] == 10) {
                //System.out.println("spare");
                bonus += frames[i+1];
            }

            sum += frames[i];
        }
        return sum+bonus;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append('[');
        for (int i = 0; i < 21; i++) {
            str.append(frames[i]);
            if(i!=20) str.append(", ");
        }
        return str.append("]").toString();
    }

//    public static void main(String[] args) {
//        BowlingGame game = new BowlingGame();
//
//        for (int i = 0; i < 10; i++) {
//            game.roll(10);
//        }
//        game.roll(10);
//        game.roll(10);
//
//        System.out.println(game);
//
//
//    }
}
