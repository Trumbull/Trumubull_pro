package ru.trumbull_pro.rebulicv_1_0;

import java.util.concurrent.TimeUnit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

public class Mining_service extends Service {
	NotificationManager nm;
	DatabaseHelper dbHelper;
	SQLiteDatabase db;
	int sum_titan;
	String name_titan;
	int sum_platinum;
	String name_platinum;
	String name_rhodium;
	int sum_rhodium;

	@Override
	public void onCreate() {
		super.onCreate();
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		dbHelper = new DatabaseHelper(this);
		db = dbHelper.getWritableDatabase();
		// ������ ������ ���� ������ �� ������� mytable, �������� Cursor

	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sendNotif();
		return super.onStartCommand(intent, flags, startId);
	}

	@SuppressWarnings("deprecation")
	void sendNotif() {
		// 1-� �����
		Notification notif = new Notification(R.drawable.ic_launcher,
				"���� ������...", System.currentTimeMillis());

		// 3-� �����
		Intent intent = new Intent(this, Miner.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		// 2-� �����

		titan();
		platinum();
		rhodium();
		notif.setLatestEventInfo(this, "������ �� ������ ������", name_titan
				+ " : " + sum_titan + "  " + name_platinum + " : "
				+ sum_platinum + " " + name_rhodium + " : " + sum_rhodium, pIntent);

		// ������ ����, ����� ����������� ������� ����� �������
		notif.flags |= Notification.FLAG_AUTO_CANCEL;

		// ����������
		nm.notify(1, notif);
	}

	public IBinder onBind(Intent arg0) {
		return null;
	}

	public void titan() {
		Cursor c = db.query("minerals", null, "id = " + "1", null, null, null,
				null);

		if (c.moveToFirst()) {

			// ���������� ������ �������� �� ����� � �������
			int nameColIndex = c.getColumnIndex("name");
			name_titan = c.getString(nameColIndex);// ������� �������������, ��
													// ������� �� �������� :)
			int totalSumColIndex = c.getColumnIndex("total_sum");
			sum_titan = c.getInt(totalSumColIndex);

		} else {
			c.close();
		}

		c.close();
	}

	public void platinum() {
		Cursor c2 = db.query("minerals", null, "id = " + "3", null, null, null,
				null);

		if (c2.moveToFirst()) {

			// ���������� ������ �������� �� ����� � �������
			int nameColIndex = c2.getColumnIndex("name");
			name_platinum = c2.getString(nameColIndex);// ������� �������������,
														// �� ������� ��
														// �������� :)
			int totalSumColIndex = c2.getColumnIndex("total_sum");
			sum_platinum = c2.getInt(totalSumColIndex);

		} else {
			c2.close();
		}

		c2.close();
	}

	public void rhodium() {
		Cursor c3 = db.query("minerals", null, "id = " + "2", null, null, null,
				null);

		if (c3.moveToFirst()) {

			// ���������� ������ �������� �� ����� � �������
			int nameColIndex = c3.getColumnIndex("name");
			name_rhodium = c3.getString(nameColIndex);// ��p����� �������������,
														// �� ������� ��
														// �������� :)
			int totalSumColIndex = c3.getColumnIndex("total_sum");
			sum_rhodium = c3.getInt(totalSumColIndex);

		} else {
			c3.close();
		}

		c3.close();
	}
}
