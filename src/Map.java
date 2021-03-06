import java.awt.Point;

public class Map {
    private final int size = 10;
    Cell[][] array = new Cell[size][size];

    public Map(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                array[i][j] = new Cell(i,j);
            }
        }
    }

    public void setPiecePosition(Piece p){
        Point pos = p.getPosition();
        double x = pos.getX();
        double y = pos.getY();
        array[(int)x][(int)y].setPiece(p);
    }

    public void display(String color){
        //if the display is for the player with the red pieces
        if(color.equalsIgnoreCase("RED")){
            System.out.print("   ");
            for(int i = 0; i < 10; i++){
                System.out.print(i + " ");
            }
            System.out.println();
            for(int i = 0; i < 10; i++){
                System.out.print(i);
                System.out.print("| ");
                for(int j = 0; j < 10; j ++){
                    //If the cell is empty, print out an empty space
                    if(array[i][j].getPiece() == null){
                        System.out.print("  ");
                    }
                    else if(array[i][j].getPieceColor().equalsIgnoreCase("BLUE") && !array[i][j].getPieceRevealed()){
                        System.out.print("+ ");
                    }
                    //otherwise print out the value of the piece occupying the cell
                    else{
                        //switch statement for printing out special piece values (lake, bomb, flag, spy, marshal)
                        switch(array[i][j].getPieceValue()){
                            case -3:
                                System.out.print("# ");
                                break;
                            case -1:
                                System.out.print("B ");
                                break;
                            case 0:
                                System.out.print("F ");
                                break;
                            case 1:
                                System.out.print("S ");
                                break;
                            case 10:
                                System.out.print("0 ");
                                break;
                            //default: just print the value of the piece
                            default:
                                System.out.print(array[i][j].getPieceValue() + " ");
                                break;
                        }
                    }
                }
                System.out.println("|");
            }
        }
        //if the display is for the player with the blue pieces
        else if(color.equalsIgnoreCase("BLUE")){
            System.out.print("   ");
            for(int i = 0; i < 10; i++){
                System.out.print(i + " ");
            }
            System.out.println();
            for(int i = 0; i < 10; i++){
                System.out.print(i);
                System.out.print("| ");
                for(int j = 0; j < 10; j ++){
                    //If the cell is empty, print out an empty space
                    if(array[i][j].getPiece() == null){
                        System.out.print("  ");
                    }
                    else if(array[i][j].getPieceColor().equalsIgnoreCase("RED") && !array[i][j].getPieceRevealed()){
                        System.out.print("+ ");
                    }
                    //otherwise print out the value of the piece occupying the cell
                    else{
                        //switch statement for printing out special piece values (lake, bomb, flag, spy, marshal)
                        switch(array[i][j].getPieceValue()){
                            case -3:
                                System.out.print("# ");
                                break;
                            case -1:
                                System.out.print("B ");
                                break;
                            case 0:
                                System.out.print("F ");
                                break;
                            case 1:
                                System.out.print("S ");
                                break;
                            case 10:
                                System.out.print("0 ");
                                break;
                            //default: just print the value of the piece
                            default:
                                System.out.print(array[i][j].getPieceValue() + " ");
                                break;
                        }
                    }
                }
                System.out.println("|");
            }
        }
        //show full board
        else{
            System.out.print("   ");
            for(int i = 0; i < 10; i++){
                System.out.print(i + " ");
            }
            System.out.println();
            for(int i = 0; i < 10; i++){
                System.out.print(i);
                System.out.print("| ");
                for(int j = 0; j < 10; j ++){
                    //If the cell is empty, print out an empty space
                    if(array[i][j].getPiece() == null){
                        System.out.print("  ");
                    }
                    //otherwise print out the value of the piece occupying the cell
                    else{
                        //switch statement for printing out special piece values (lake, bomb, flag, spy, marshal)
                        switch(array[i][j].getPieceValue()){
                            case -3:
                                System.out.print("# ");
                                break;
                            case -1:
                                System.out.print("B ");
                                break;
                            case 0:
                                System.out.print("F ");
                                break;
                            case 1:
                                System.out.print("S ");
                                break;
                            case 10:
                                System.out.print("0 ");
                                break;
                            //default: just print the value of the piece
                            default:
                                System.out.print(array[i][j].getPieceValue() + " ");
                                break;
                        }
                    }
                }
                System.out.println("|");
            }
        }
    }

    //check if a move is valid, and then update the map accordingly
    //prev is the square the moving piece was, and now is the square
    //the piece is trying to move to
    public void movePiece(Point start, Point end){
        int startX = (int)start.getX();
        int startY = (int)start.getY();
        int endX = (int)end.getX();
        int endY = (int)end.getY();

        array[endX][endY].setPiece(array[startX][startY].getPiece());
        array[startX][startY].emptyCell();
        /*
        //calculate if the positions are adjacent
        int x = prev[0] - now[0];
        int y = prev[1] - now[1];
        int a = x + y;
        //check if the move is diagonal
        if(x != 0 && y != 0){
            return false;
        }
        //cannot move into lake
        //cannot move bomb or flag pieces
        else if(array[now[0]][now[1]] == -3 || array[prev[0]][prev[1]] == -1 || array[prev[0]][prev[1]] == 0){
            return false;
        }
        //cannot move a piece onto a piece of the same color
        else if(color[prev[0]][prev[1]] == color[now[0]][now[1]]){
            return false;
        }
        //invalid if the positions are on top of each other, 
        //or not adjacent when the piece is not a scout
        else if(array[prev[0]][prev[1]] != 2 && a != 1){
            return false;
        }
        //cannot move a piece diagonally
        else{
            if(array[now[0]][now[1]] == -1){
                //if a piece that is a miner moves onto a square with a bomb
                //the square is now the miner
                //if not, the square is still a bomb
                if(array[prev[0]][prev[1]] == 3){
                    array[now[0]][now[1]] = 3;
                    color[now[0]][now[1]] = color[prev[0]][prev[1]];
                }
                //the previous square is now empty
                array[prev[0]][prev[1]] = -2;
                color[prev[0]][prev[1]] = 0;
                
            }
            //if the piece is the scout, make sure it doesn't go over lakes.
            else if(array[prev[0]][prev[1]] == 2){
                //making a move horizontally over the lake
                if(x != 0){
                    if(prev[0] == 4 || prev[0] == 5){
                        if(prev[1] <= 3 && now[1] >= 2){
                            return false;
                        }
                        else if(prev[1] >= 2 && now[1] <= 3){
                            return false;
                        }
                        else if(prev[1] <= 7 && now[1] >= 6){
                            return false;
                        }
                        else if(prev[1] >= 6 && now[1] <= 7){
                            return false;
                        }
                    }
                }
                //making a move vertically over the lake
                else if(y != 0){
                    if(prev[1] == 2 || prev[1] == 3 || prev[1] == 6 || prev[1] == 7){
                        if(prev[0] <= 5 && now[0] >= 4){
                            return false;
                        }
                        else if(prev[0] >= 4 && now[0] <= 5){
                            return false;
                        }
                    }
                }
            }
            //now all the rules have been gone over and the piece is able
            //to actually make a move
            
            //if the value of the piece1 in the previuos square is greater than
            //the piece2 currently in the square, piece1 replaces piece2
            //otherwise, piece2 remains
            if(array[prev[0]][prev[1]] > array[now[0]][now[1]]){
                array[now[0]][now[1]] = array[prev[0]][prev[1]];
                color[now[0]][now[1]] = color[prev[0]][prev[1]];
            }
            //the previous square is now empty;
            array[prev[0]][prev[1]] = -2;
            color[prev[0]][prev[1]] = 0;
        }*/
    }
    
    public Cell getCell(int i, int j){
        return array[i][j];
    }

    public Cell getEmptyCell(int i, int j){
        return new Cell(i, j);
    }
}
