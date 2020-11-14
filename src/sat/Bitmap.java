package sat;

public class Bitmap extends Data {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int[][] picture;

    public Bitmap(int n, int m) {
        super("Bitmap");
        picture = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double valueij = 255 * Math.random();
                picture[i][j] = (int) valueij;
            }
        }
    }

}
