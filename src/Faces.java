import java.io.*;
import java.util.*;

/**
 * @author Alexaner Gunillasson(id14agn) & Simon Edman(c11sen)
 *
 */

public class Faces {


    public static void main(String[] args) throws IOException {

        Faces faces = new Faces();

        HashMap<String, Integer[][]> trainingImages;
        HashMap<String, Integer> trainingFacit;
        HashMap<String, Integer[][]> testImages;

        trainingImages = faces.readTrainingFile(args[0]);
        trainingFacit = faces.readFacit(args[1]);
        testImages = faces.readTrainingFile(args[2]);

        Set<String> list = new HashSet<>(trainingImages.keySet());
        int s = list.size();

        Map<String, Integer[][]> linkMap = new LinkedHashMap<>();

        for (String k : list) {
            linkMap.put(k, trainingImages.get(k));
        }

        HashMap<String, Integer[][]> trainingSet = new HashMap<>();
        HashMap<String, Integer[][]> performanceSet = new HashMap<>();
        int i = 0;
        Network network = new Network();
        for (Map.Entry<String, Integer[][]> entry : linkMap.entrySet()) {

            String key = entry.getKey();
            Integer[][] image = entry.getValue();

            if (i <= (s * 3/4)) {
                trainingSet.put(key, image);
            }
            if (i > (s /4)) {
                performanceSet.put(key, image);
            }
            i++;
        }
        int z = 0;
        while (z < 80) {
            network.train(trainingSet, trainingFacit);
            network.checkResult(performanceSet, false);
            z++;
        }
        network.checkResult(testImages, true);
    }

    private HashMap<String, Integer[][]> readTrainingFile(String trainingFile) throws FileNotFoundException {

        Scanner scanner = new Scanner(new FileReader(trainingFile));
        String tmp;
        HashMap<String, Integer[][]> imageMap = new HashMap<>();

        while (scanner.hasNextLine()) {
            tmp = scanner.nextLine();

            if (!tmp.contains("#")) {

                if (tmp.contains("Image")) {

                    scanner.useDelimiter("\\s");
                    Integer[][] image = new Integer[20][20];
                    for (int i = 0; i < 20; i++) {
                        for (int j = 0; j < 20; j++) {
                            int value = scanner.nextInt();
                            image[j][i] = value;
                        }
                    }
                    imageMap.put(tmp, image);
                }
            }
        }
        scanner.close();
        return imageMap;
    }

    private HashMap<String, Integer> readFacit(String facitFile) throws FileNotFoundException {

        Scanner scanner = new Scanner(new FileReader(facitFile));
        String tmp;
        HashMap<String, Integer> imageFacit = new HashMap<>();

        while(scanner.hasNext()){
            tmp = scanner.next();

            if(!tmp.contains("#")){

                if(tmp.contains("Image")){
                    scanner.useDelimiter("\\s");

                    int correctAnswer = scanner.nextInt();
                    imageFacit.put(tmp, correctAnswer);
                }
            }
        }
        scanner.close();
        return imageFacit;
    }

}
