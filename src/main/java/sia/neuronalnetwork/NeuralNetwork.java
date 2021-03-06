package sia.neuronalnetwork;

import sia.neuronalnetwork.function.TransferFunction;

public class NeuralNetwork {

	private TransferFunction f;
	private Layer[] layers;
	/**
	 * Crea una red con input de largo structure[0]. Todos los demas valores que
	 * se encuentren entre 1 y structure.length - 1 son creados como capas
	 * ocultas.
	 */
	public NeuralNetwork(int[] structure) {
		if (structure.length < 2) {
			throw new IllegalArgumentException(
					"Net must have at least one layer");
		}
		layers = new Layer[structure.length - 1];
		for (int i = 1; i < structure.length; i++) {
			layers[i - 1] = new Layer(structure[i - 1], structure[i]);
		}
	}

	public void setTransferFunction(TransferFunction f) {
		this.f = f;
	}
	
	public TransferFunction getTransferFunction() {
		return f;
	}
	
	public NeuralNetwork(Layer[] layers) {
		setLayers(layers);
	}

	public void setLayers(Layer[] layers) {
		this.layers = layers;
	}

	public float[] evaluate(float[] input) {
		float[] aux = input;
		for (Layer l : layers) {
			aux = l.evaluate(aux, f);
		}
		return aux;
	}

	public Layer getLayer(int index) {
		return layers[index];
	}

	public Layer[] getLayers() {
		return layers;
	}

	public int getTotalLayers() {
		return layers.length;
	}

	public boolean equals(NeuralNetwork net) {
		if (layers.length != net.layers.length) {
			return false;
		}
		for (int i = 0; i < layers.length; i++) {
			if (!layers[i].equals(net.layers[i])) {
				return false;
			}
		}
		return true;
	}
}
