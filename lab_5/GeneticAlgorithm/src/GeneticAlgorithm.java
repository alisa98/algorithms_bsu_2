
import java.util.Random;

public class GeneticAlgorithm {

    public static int geneMin = 1;
    public static int geneMax = 40;
    public static int targetValue = 45;
    public static int valueCount = 3;
    public static float mutationProbability = 6.0F;
    public static int populationCount = 10;
    public static int maxIterations = 10000;

    private Individual population[] = new Individual[populationCount];

    private int fillIndividualWithFitnesses() {
        for (int i = 0; i < populationCount; ++i) {

            float currentFitness = population[i].calculateFitness();
            population[i].setFitness(currentFitness);

            if (currentFitness == -1)
                return i;
        }
        return -2;
    }

    public static int function(int x, int y, int z) {
        return x*x*y*y*z*z-2*x*y-3*x-4*y-z;
    }

    private float getAllFitnessesSum() {
        float allFitnessesSum = 0.0F;
        for (int i = 0; i < populationCount; ++i) {
            allFitnessesSum += population[i].getFitness();
        }
        return allFitnessesSum;
    }

    private void fillIndividualWithProbability() {
        float allFitnessesSum = getAllFitnessesSum();
        float last = 0.0F;
        for (int i = 0; i < populationCount; ++i) {

            float probability = last + (100 * population[i].getFitness() / allFitnessesSum);
            last = probability;
            population[i].setProbability(probability);
        }
    }

    private void printAllIndividual() {
        for (int i = 0; i < populationCount; ++i) {
            log(population[i].toString());
        }
    }

    public static void log(String message) { }

    public static int getRandomInt(int min, int max) {
        Random randomGenerator;
        randomGenerator = new Random();
        return randomGenerator.nextInt(max + 1) + min;
    }

    public static float getRandomFloat(float min, float max) {
        return (float) (Math.random() * max + min);
    }

    public static int getRandomGene() {
        return getRandomInt(geneMin, geneMax);
    }

    private void fillIndividualWithRandomGenes(Individual individual) {
        for (int i = 0; i < valueCount; ++i) {
            individual.getGenes()[i] = getRandomGene();
        }
    }

    private void createInitialPopulation() {
        for (int i = 0; i < populationCount; ++i) {
            population[i] = new Individual();
            fillIndividualWithRandomGenes(population[i]);
        }
    }

    private int[][] getPairsForCrossover() {

        int[][] pairs = new int[populationCount][2];
        for (int i = 0; i < populationCount; ++i) {
            float random = getRandomFloat(0, 100);
            int firstIndividual = getIndividualNumberForThisRand(random);

            int secondIndividual;

            do {
                random = getRandomFloat(0, 100);
                secondIndividual = getIndividualNumberForThisRand(random);
            } while (firstIndividual == secondIndividual);

            pairs[i][0] = firstIndividual;
            pairs[i][1] = secondIndividual;
        }
        return pairs;
    }


    private void analizePairs(int[][] pairs) {

        int[] totals = new int[populationCount];

        for (int i = 0; i < populationCount; ++i) {
            totals[i] = 0;
        }
        for (int i = 0; i < populationCount; ++i) {
            for (int j = 0; j < 2; ++j) {
                totals[pairs[i][j]]++;
            }
        }
    }

    private int getIndividualNumberForThisRand(float random) {
        int i;
        for (i = 0; i < populationCount; ++i) {

            if (random <= population[i].getProbability()) {
                return i;
            }
        }
        return i - 1;
    }

    private Individual[] performCrossoverAndMutationForThePopulationAndGetNextGeneration(int[][] pairs) {

        Individual nextGeneration[] = new Individual[populationCount];

        for (int i = 0; i < populationCount; ++i) {
            Individual firstParent = population[pairs[i][0]];
            Individual secondParent = population[pairs[i][1]];

            Individual result = firstParent.singlePointCrossing(secondParent);
            nextGeneration[i] = result;

            nextGeneration[i] = nextGeneration[i].mutateWithGivenProbability();
        }
        return nextGeneration;
    }

    public Individual[] getPopulation() {
        return population;
    }

    public void setPopulation(Individual[] population) {
        this.population = population;
    }

    public static void main(String[] args) {

        System.out.println("Diophantine equation x*x*y*y*z*z-2*x*y-3*x-4*y-z=45" + "\n");
        GeneticAlgorithm diofantEquation = new GeneticAlgorithm();
        diofantEquation.createInitialPopulation();
        Individual individual = null;
        long iterationsNumber = 0;

        while (iterationsNumber++ < maxIterations)
        {
            int fillingWithFitnessesResult = diofantEquation.fillIndividualWithFitnesses();

            if (fillingWithFitnessesResult != -2) {
                individual = diofantEquation.getPopulation()[fillingWithFitnessesResult];
                break;
            }

            diofantEquation.fillIndividualWithProbability();
            diofantEquation.printAllIndividual();
            int[][] pairs = diofantEquation.getPairsForCrossover();
            diofantEquation.analizePairs(pairs);

            Individual nextGeneration[] = new Individual[populationCount];

            nextGeneration = diofantEquation.performCrossoverAndMutationForThePopulationAndGetNextGeneration(pairs);

            diofantEquation.setPopulation(nextGeneration);
        }

        if (individual != null) {
            System.out.println("Solution is found: " + individual);
            System.out.println("iterations " + iterationsNumber);
        } else {
            System.out.println("no solution found :(");
        }


    }

}