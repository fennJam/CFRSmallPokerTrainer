package cfr.trainer_multithreaded;

public class SynchronisedRegretsArray {
	private static double[] regretArray;

	public SynchronisedRegretsArray(int size) {
		regretArray = new double[size];
	}

	public void setValue(int index, double value) {
		synchronized (regretArray) {
			regretArray[index] = value;
		}
	}

	public double getValue(int index) {
		synchronized (regretArray) {
			return regretArray[index];
		}
	}

	public int getLength() {
		synchronized (regretArray) {
			return regretArray.length;
		}
	}

	public double[] getArray() {
		synchronized (regretArray) {
			return regretArray;
		}
	}

}
