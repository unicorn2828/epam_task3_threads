package by.kononov.multithreading.entity;

public class ParkingPlace{
	private long placeId;
	private boolean status;

	public ParkingPlace(long id) {
		this.placeId = id;
	}

	public long getPlaceId() {
		return placeId;
	}

	public void using(boolean isUsing) {
		status = isUsing;
	}

	public boolean getStatus() {
		return status;
	}
}