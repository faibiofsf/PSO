import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PSO {

	double w, c1, c2, r1, r2, xMin, xMax, vMin, vMax, wMin, wMax, phi, chi, nInfinite, gBestValue, pInfinite,
			gWorstValue, gMediumValue;

	ArrayList<Particula> populacao;

	int NumeroParticulas, NumeroVariaveis, NumeroIteracoes;

	public PSO() {
		super();
		// TODO Auto-generated constructor stub
		w = 0.9;
		c1 = 2.05;
		c2 = 2.05;
		r1 = 0.0;
		r2 = 0.0;
		xMin = -100.0;
		xMax = 100.0;
		vMin = 0;
		vMax = 1;
		wMin = 0.4;
		wMax = 0.9;
		phi = c1 + c2;
		chi = 2.0 / Math.abs(2.0 - phi - Math.sqrt(Math.pow(phi, 2) - 4 * phi));

		nInfinite = Double.NEGATIVE_INFINITY;
		gBestValue = nInfinite;

		pInfinite = Double.POSITIVE_INFINITY;
		gWorstValue = pInfinite;

		NumeroParticulas = 1000; // Numero de particulas
		NumeroVariaveis = 3; // Numero de variaveis
		NumeroIteracoes = 2000; // Numero de iterações

		populacao = new ArrayList<Particula>();

	}

	public void run() {

		double[] gBestPosition = new double[NumeroVariaveis], bestFitnessHistory = new double[NumeroIteracoes];

		Random rand = new Random(123456);

		for (int p = 0; p < NumeroParticulas; p++) {
			populacao.add(new Particula());
		}

		for (int p = 0; p < NumeroParticulas; p++) {
			ArrayList<Particula> vizinhanca = new ArrayList<Particula>();
			for (int v = 0; v < (int) NumeroParticulas / 100; v++) {
				vizinhanca.add(populacao.get(rand.nextInt(NumeroParticulas)));
			}
			populacao.get(p).setVizinhanca(vizinhanca);
		}

		for (int p = 0; p < NumeroParticulas; p++) {

			double[] variaveisParticula = new double[NumeroVariaveis];
			double[] velocidadeParticula = new double[NumeroVariaveis];

			for (int i = 0; i < NumeroVariaveis; i++) {

				double variavel = xMin + (xMax - xMin) * rand.nextDouble();
				double velocidade = vMin + (vMax - vMin) * rand.nextDouble();

				if (rand.nextDouble() < 0.5) {
					velocidade = -velocidade;
					variavel = -variavel;
				}

				variaveisParticula[i] = variavel;
				velocidadeParticula[i] = velocidade;
			}

			populacao.get(p).setValorReal(variaveisParticula);
			populacao.get(p).setVelocidade(velocidadeParticula);

		}

		// Avalia
		for (int p = 0; p < NumeroParticulas; p++) {

			double avaliacao = schafferF6(populacao.get(p).getValorReal());
			populacao.get(p).setFitness(avaliacao);

		}

		rank(populacao);

		for (int j = 0; j < NumeroIteracoes; j++) {
			for (int p = 0; p < NumeroParticulas; p++) {

				double[] variaveisParticula = populacao.get(p).getValorReal();

				for (int i = 0; i < NumeroVariaveis; i++) {

					variaveisParticula[i] = variaveisParticula[i] + populacao.get(p).getVelocidade()[i];

					if (variaveisParticula[i] > xMax) {
						variaveisParticula[i] = xMax;
					} else if (variaveisParticula[i] < xMin) {
						variaveisParticula[i] = xMin;
					}
				}
			}

			for (int p = 0; p < NumeroParticulas; p++) {

				double avaliacao = schafferF6(populacao.get(p).getValorReal());
				populacao.get(p).setFitness(avaliacao);

				// Atualiza melhor valor da particula
				if (populacao.get(p).getFitness() > populacao.get(p).getBestFitness()) {
					populacao.get(p).setBestFitness(populacao.get(p).getFitness());
					populacao.get(p).setBestPosition(populacao.get(p).getValorReal());
				}

				// Atualiza melhor valor global
				if (populacao.get(p).getFitness() > gBestValue) {
					gBestValue = populacao.get(p).getFitness();
					gBestPosition = populacao.get(p).getValorReal();
				}

			}

			rank(populacao);
			System.out.println("Iteracao: " + j + " MelhorValor " + populacao.get(0).getFitness());

			bestFitnessHistory[j] = gBestValue;

			w = wMax - ((wMax - wMin) / NumeroIteracoes) * j;
			for (int p = 0; p < NumeroParticulas; p++) {
				rank(populacao.get(p).getVizinhanca());

				double[] velocidadeParticula = new double[NumeroVariaveis];

				for (int i = 0; i < NumeroVariaveis; i++) {

					r1 = rand.nextDouble();
					r2 = rand.nextDouble();

					velocidadeParticula[i] = chi * w
							* (populacao.get(p).getValorReal()[i]
									+ r1 * c1
											* (populacao.get(p).getBestPosition()[i]
													- populacao.get(p).getValorReal()[i])
									+ r2 * c2 * (populacao.get(p).getVizinhanca().get(0).getValorReal()[i]-populacao.get(p).getValorReal()[i]));

					// Caso ultrapasse os limites
					if (velocidadeParticula[i] > vMax) {
						velocidadeParticula[i] = vMax;
					} else if (velocidadeParticula[i] < vMin) {
						velocidadeParticula[i] = vMin;
					}

					populacao.get(p).setVelocidade(velocidadeParticula);
				}
			}
			// imprime o melhor encontrado
			//System.out.println("Iteracao: " + j + " MelhorValor " + gBestValue);
		}
	}

	public void rank(ArrayList<Particula> vizinhanca) {
		Collections.sort(vizinhanca);
	}

	public double schafferF6(double[] x) {
		double temp1 = 0.0;
		for (int i = 0; i < x.length; i++) {
			temp1 += x[i] * x[i];
		}
		double temp2 = Math.sin(Math.sqrt(temp1));
		double temp3 = 1.0 + 0.001 * temp1;
		return (0.5 - ((temp2 * temp2 - 0.5) / (temp3 * temp3)));

	}

	public static void main(String[] args) {

		PSO pso = new PSO();
		pso.run();

	}
}
