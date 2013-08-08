package TOI.model;

import java.util.Date;

public class SendOrder {
	int id;
	String tid;
	Date payTime;
	Date mtime;
    String buyerNIck;
	String senderName;
	String senderTele;
	String senderAdress;
	String receiverName;
	String recerverState;
	String receiverCity;
	String receiverAddress;
	String receiverMobile;

    public String getBuyerNIck() {
        return buyerNIck;
    }

    public void setBuyerNIck(String buyerNIck) {
        this.buyerNIck = buyerNIck;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    String receiverPhone;
	int status;
	String sellerMemo;
	double price;
	String expressNum;
	String expressId;
	int sellerFlag;



	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}




	public String getExpressNum() {
		return expressNum;
	}

	public void setExpressNum(String expressNum) {
		this.expressNum = expressNum;
	}

	public int getSellerFlag() {
		return sellerFlag;
	}

	public void setSellerFlag(int sellerFlag) {
		this.sellerFlag = sellerFlag;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public String getRecerverState() {
		return recerverState;
	}

	public void setRecerverState(String recerverState) {
		this.recerverState = recerverState;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSellerMemo() {
		return sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderAdress() {
		return senderAdress;
	}

	public void setSenderAdress(String senderAdress) {
		this.senderAdress = senderAdress;
	}

	public String getSenderTele() {
		return senderTele;
	}

	public void setSenderTele(String senderTele) {
		this.senderTele = senderTele;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

}
