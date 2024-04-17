

public class Permutation {

	int[] permutation;

	public Permutation(int[] permutation) {
		this.permutation = permutation;
	}

	//This ° other = result
	public Permutation link(Permutation other) {
		int[] result = new int[other.getSize()];
		for (int i = 0; i < this.permutation.length; i++) {
			result[i] = this.permutation[other.getEntry(i) - 1];
		}

		return new Permutation(result);
	}

	private int getEntry(int i) {
		return this.permutation[i];
	}
	
	public int getSize() {
		return this.permutation.length;
	}

	public int getOrder() {
		int limit = 100;
		int order = 1;
		Permutation temp = this;
		while (!temp.equalsNeutral() && order != limit) {
			temp = this.link(temp);
			order++;
		}
		return order;
	}
	
	public boolean equals(Permutation other) {
		if (this.permutation.length != other.getSize())
			return false;

		for (int i = 0; i < this.permutation.length; i++) {
			if (this.permutation[i] != other.getEntry(i))
				return false;
		}

		return true;
	}

	public void printPermutation() {
		for (int i = 0; i < this.permutation.length; i++)
			System.out.print(i + 1 + " ");

		System.out.println();
		for (int i = 0; i < this.permutation.length; i++)
			System.out.print(this.permutation[i] + " ");

		System.out.println();
		System.out.println("------------");
	}

	public boolean equalsNeutral(/*Permutation other*/) {
		for (int i = 0; i < this.permutation.length; i++) {
			if (this.permutation[i] != i + 1)
				return false;
		}

		/*for (int i = 0; i < other.getSize(); i++) {
			if (other.getEntry(i) != i + 1)
				return false;
		}*/

		return true;
	}

	public double getSignum() {
		double result = 1;
		for (int i = 1; i < permutation.length; i++) {
			for (int j = 0; j < i; j++) {
				result *= (double) (permutation[i] - permutation[j]) / (i - j);
			}
		}
		return result;
	}

}
