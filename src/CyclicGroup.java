

public class CyclicGroup extends CollectionPermutation {

	public CyclicGroup(Permutation permutation) {
		Permutation temp = permutation;
		this.permutations.add(temp);

		while (!temp.equalsNeutral()) {
			temp = temp.link(permutation);
			this.permutations.add(temp);
		} 
	}

}
