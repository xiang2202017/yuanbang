package com.health.entity.system;

/**
 * 会员续约
 * @author XiangYu
 *
 */
public class MemberRenew {
	
	private Integer id;
	private Integer memberId;
	private Integer requestTerm;		//申请期限
	private Integer status;
	private String createTime;
	private String dealTime;
	private String failReason;
	private String memberNo;
	private String memberName;
	
	
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getRequestTerm() {
		return requestTerm;
	}
	public void setRequestTerm(Integer requestTerm) {
		this.requestTerm = requestTerm;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	
	
}
