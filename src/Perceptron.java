import java.util.ArrayList;

/**
 * @author Alexaner Gunillasson(id14agn) & Simon Edman(c11sen)
 * Class Perceptron
 *
 */
public class Perceptron {
    private double learningRate;
    private int faceExpression;
    private ArrayList<Double> randWeights;
    private double activationValue;
    private double error;

    /**
     * Constructor
     * @param learningRate The learning rate for the perceptron
     * @param faceExpression The face expression that the perceptron is trained
     *                       to recognize.
     */
    public Perceptron(double learningRate, int faceExpression){

        this.learningRate = learningRate;
        this.faceExpression = faceExpression;

        randWeights = new ArrayList<>();

        for (int i = 0; i < 400; i++){
            randWeights.add(Math.random());
        }

        // bias
        randWeights.add(1.0);
    }

    /**
     * trains an image
     * @param image Integer matrix containing the pixels in an image
     * @param desiredOutput the value the face expression should be
     */
    public void train(Integer[][] image, int desiredOutput){
        calcWeightedSum(image);
        adjustWeights(image, desiredOutput);
    }

    /**
     * calculates a sum of the weights in a image and then passes the sum
     * through the activation function
     * @param image Integer matrix containing the pixels in an image
     * @return returns the sum of weights passed through the activation function
     */
    public double calcWeightedSum(Integer[][] image){

        double sum = 0.0;
        int x = 0;
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 20; j++){

                int temp = image[j][i];
                sum += (double)temp * randWeights.get(x);
                x++;
            }
            // adds bias
            sum += randWeights.get(400);
        }
        return this.activationValue = (1 / (1 + Math.exp(-sum)));
    }

    /**
     * Calculates the error between the desired value and the actual value
     * @param desiredOutput the value the face expression should be
     * @return returns an error
     */
    public double calculateError(int desiredOutput){

        return (desiredOutput - activationValue);
    }

    /**
     * Adjusts the weights based on each image and desired output
     * @param image Integer matrix containing the pixels in an image
     * @param desiredOutput the value the face expression should be
     */
    public void adjustWeights(Integer[][] image, int desiredOutput){

        this.error = calculateError(desiredOutput);
        int x = 0;

        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 20; j++){
                int temp = image[j][i];
                double deltaWeight = error * temp * learningRate;
                double weight = (randWeights.get(x) + deltaWeight);
                randWeights.set(x, weight);
                x++;
            }
        }
    }

    /**
     * gets the face expression
     * @return face expression value
     */
    public int getFaceExpression(){

        return faceExpression;
    }

}
