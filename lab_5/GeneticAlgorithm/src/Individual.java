

public class Individual {

    private int genes[] = new int[GeneticAlgorithm.valueCount];
    private float fitness;
    private float probability;

    public float getFitness() {return fitness;    }
    public void setFitness(float fitness) { this.fitness = fitness;    }
    public int[] getGenes() {
        return genes;
    }
    public void setGenes(int[] genes) {
        this.genes = genes;
    }
    public float getProbability() {
        return probability;
    }
    public void setProbability(float probability) {
        this.probability = probability;
    }

    public float calculateFitness() {
        int x = genes[0];
        int y = genes[1];
        int z = genes[2];
        int closeness = Math.abs(GeneticAlgorithm.targetValue - GeneticAlgorithm.function(x, y, z));
        if (0 != closeness)
        {
            return 1 / (float) closeness;
        }
        else {
            return -1;
        }
    }

    public Individual mutateWithGivenProbability() {

        Individual result = (Individual) this.clone();

        for (int i = 0; i < GeneticAlgorithm.valueCount; ++i) {
            float randomPercentage = GeneticAlgorithm.getRandomFloat(0, 100);
            if (randomPercentage < GeneticAlgorithm.mutationProbability) {
                int newValue = GeneticAlgorithm.getRandomGene();
                result.getGenes()[i] = newValue;
            }
        }
        return result;
    }

    public Individual[] twoPointCrossing(Individual individual) {

        int crossoverLine = getRandomCrossoverLine();
        Individual[] result = new Individual[2];
        result[0] = new Individual();
        result[1] = new Individual();

        for (int i = 0; i < GeneticAlgorithm.valueCount; ++i) {
            if (i <= crossoverLine) {
                result[0].getGenes()[i] = this.getGenes()[i];
                result[1].getGenes()[i] = individual.getGenes()[i];

            } else {
                result[0].getGenes()[i] = individual.getGenes()[i];
                result[1].getGenes()[i] = this.getGenes()[i];
            }
        }
        return result;
    }

    public Individual singlePointCrossing(Individual individual) {
        Individual[] children = twoPointCrossing(individual);
        int childNumber = GeneticAlgorithm.getRandomInt(0, 1);
        return children[childNumber];
    }


    private static int getRandomCrossoverLine() {
        int line = GeneticAlgorithm.getRandomInt(0, GeneticAlgorithm.valueCount - 2);
        return line;
    }

    protected Object clone() {
        Individual resultIndividual = new Individual();
        resultIndividual.setFitness(this.getFitness());
        resultIndividual.setProbability(this.getProbability());
        int resultGenes[] = new int[GeneticAlgorithm.valueCount];
        resultGenes = this.genes.clone();
        resultIndividual.setGenes(resultGenes);
        return resultIndividual;
    }

    public String toString() {

        StringBuffer result = new StringBuffer();
        result.append("(x,y,z)= (");

        for (int i = 0; i < GeneticAlgorithm.valueCount; ++i) {
            result.append("" + genes[i]);
            result.append(i < GeneticAlgorithm.valueCount - 1 ? ", " : "");
        }
        result.append(")");
        return result.toString();
    }
}