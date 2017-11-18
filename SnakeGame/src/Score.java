
public class Score implements Comparable<Score>{

    private String rank;
    private String name;
    private int score;

    public Score(String rank, String name, int score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }
    public Score(String line){
        String splits[]=line.split("\t");
        this.rank=splits[0];
        this.name=splits[1];
        this.score=Integer.parseInt(splits[2]);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
    
    public String getRank(){
        return rank;
    }
    
    public int getScore(){
        return score;
    }
    
    public String getName(){
        return name;
    }

    public int compareTo(Score other) {
        return this.score-other.score;
    }
}
