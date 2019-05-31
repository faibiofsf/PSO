import java.util.ArrayList;

public class Particula implements Comparable<Particula> {

	private double fitness = 0;
	private double[] XYOrigem;
	private double[] variaveis;
	private double[] velocidade;
	private double[] bestPosition;
	private double bestValue;
	
	ArrayList<Particula> vizinhanca;

	public Particula() {
		this.setFitness(Double.NEGATIVE_INFINITY);
		this.setBestFitness(Double.NEGATIVE_INFINITY);
		this.setVizinhanca(new ArrayList<Particula>());
	}


	public Particula(double[] variaveis, double[] velocidade) {
		super();
		this.setValorReal(variaveis);
		this.setVelocidade(velocidade);
		this.setBestPosition(variaveis);
		this.setFitness(Double.NEGATIVE_INFINITY);
		this.setBestFitness(Double.NEGATIVE_INFINITY);
		this.setVizinhanca(new ArrayList<Particula>());
	}

	public double getBestFitness() {
		return bestValue;
	}


	public void setBestFitness(double bestValue) {
		this.bestValue = bestValue;
	}
	
	public ArrayList<Particula> getVizinhanca() {
		return vizinhanca;
	}

	public void setVizinhanca(ArrayList<Particula> vizinhanca) {
		this.vizinhanca = vizinhanca;
	}
	
	public double[] getXYOrigem() {
		return this.XYOrigem;
	}

	public void setXYOrigem(double[] XYOrigem) {
		this.XYOrigem = XYOrigem;
	}
	
	public void setValorReal(double[] XY) {
		this.variaveis = XY;
	}

	public double[] getValorReal() {
		return this.variaveis;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double[] getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(double[] velocidade) {
		this.velocidade = velocidade;
	}

	public double[] getBestPosition() {
		return bestPosition;
	}


	public void setBestPosition(double[] bestPosition) {
		this.bestPosition = bestPosition;
	}


	@Override
	public int compareTo(Particula outroIndividuo) {
		// TODO Auto-generated method stub
		if (this.fitness > outroIndividuo.getFitness()) {
			return -1;
		}
		if (this.fitness < outroIndividuo.getFitness()) {
			return 1;
		}
		return 0;
	}
}