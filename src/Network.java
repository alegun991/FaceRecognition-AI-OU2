import java.util.*;

/**
 * @author Alexaner Gunillasson(id14agn) & Simon Edman(c11sen)
 * class Network
 * creates 4 perceptrons(nodes), one for each expression. This class trains
 * the the nodes and then checks the result compared to a facit.
 */
public class Network {


    private double learningRate = 0.01;
    private List<Perceptron> perceptrons = new ArrayList<>();

    private static final int HAPPY = 1;
    private static final int SAD = 2;
    private static final int MISCHIEVOUS = 3;
    private static final int MAD = 4;

    /**
     *
     * Constructor
     *
     */
    public Network(){

        perceptrons.add(new Perceptron(learningRate, HAPPY));
        perceptrons.add(new Perceptron(learningRate, SAD));
        perceptrons.add(new Perceptron(learningRate, MISCHIEVOUS));
        perceptrons.add(new Perceptron(learningRate, MAD));

    }

    public void train(HashMap<String, Integer[][]> images, HashMap<String, Integer> imageFacit){

        int laps = 0;
        do {
            int desiredOutput;

            Set<String> imageKeys = new HashSet<>(images.keySet());

            Map<String, Integer[][]> shuffleMap = new LinkedHashMap<>();
            for (String k : imageKeys) {
                shuffleMap.put(k, images.get(k));
            }

            for (Map.Entry<String, Integer[][]> entry : shuffleMap.entrySet()) {
                String key = entry.getKey();
                Integer[][] image = entry.getValue();
                laps ++;
                for (Perceptron perceptron : perceptrons) {

                    if (perceptron.getFaceExpression() == imageFacit.get(key)) {
                        desiredOutput = 1;
                    }
                    else {
                        desiredOutput = 0;
                    }
                    perceptron.train(image, desiredOutput);
                }
            }
        }
        while(laps < 200);

    }

    public void checkResult(HashMap<String, Integer[][]> images, boolean test){

        List<String> keys = new ArrayList<>();

        for (Map.Entry<String, Integer[][]> entry : images.entrySet()){

            Integer[][] image = entry.getValue();
            HashMap<Double, Integer> probabilities = new HashMap<>();

            for(Perceptron perceptron : perceptrons){
                double value = perceptron.calcWeightedSum(image);
                probabilities.put(value, perceptron.getFaceExpression());
            }

            double maxValue = Collections.max(probabilities.keySet());
            int faceExpression = probabilities.get(maxValue);


            String guessedString = String.format("%s %d", entry.getKey(),
                    faceExpression);

            keys.add(guessedString);

        }
        if(test){
            Collections.sort(keys);
            for (int i = 0; i < images.size(); i++) {
                System.out.println(keys.get(i));
            }
        }
    }
}


