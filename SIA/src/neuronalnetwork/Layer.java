package neuronalnetwork;

import neuronalnetwork.function.TransferFunction;

public class Layer {
	
	private float[][] weights;
	private int neurons; 
	private int outputLen;
	// Used for backpropagation
	private float[] lastOutput;
	private float[] lastInput;
	
	/**
	 * Inicializa este Layer con input neuronas como 
	 * layer de salida (no crea coencciones con capas sucesivas).
	 */
	public Layer(int neurons) {
		this(neurons, neurons, false);
	}
	
	/**
	 * Inicializa este layer con input neuronas, cada
	 * una con output conecciones con la capa siguiente.
	 */
	public Layer(int neurons, int outputLen) {
		this(neurons, outputLen, true);
	}
	
	private Layer(int neurons, int outputLen, boolean outerLayer) {
		this.neurons = neurons;
		this.outputLen = outputLen;
		lastOutput = new float[outputLen];
		if (outerLayer) {
			// input + 1 because of the Bias input for each neuron
			weights = new float[neurons + 1][outputLen];			
			initWeightMantrix();
		}
	}
	
	private void initWeightMantrix() {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[0].length; j++) {
				weights[i][j] = MathUtils.random(-0.5f, 0.5f);
			}
		}
	}

	public float[][] getWeights() {
		return weights;
	}
	
	public int getNeurons() {
		return neurons;
	}
	
	public int getOutputLen() {
		return outputLen;
	}
	
	public float[] evaluate(float[] in, TransferFunction f) {
		validateInputDimention(in.length);
		lastInput = in;
		if (weights == null) {	// this is an outer layer
			return in;
		}
		for (int i = 0; i < outputLen; i++) {
			float sum = 0;
			for (int j = 0; j < neurons; j++) {
				sum += weights[j][i] * in[j];
			}
			sum -= weights[neurons][i];	// Bias
			lastOutput[i] = f.valueAt(sum);
		}
		return lastOutput;
	}
	
	private void validateInputDimention(int dim) {
		if (dim != neurons) {
			throw new IllegalArgumentException("Invalid input dimention given: " 
				+ dim + ". Should be " + neurons);
		}
	}
	
	public float[] getLastOutput() {
		return lastOutput;
	}
	
	public float[] getLastInput() {
		return lastInput;
	}

}
