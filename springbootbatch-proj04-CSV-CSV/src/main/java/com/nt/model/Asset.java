package com.nt.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Asset {
	private Integer assetId;
	private String assetName;
	private String assetModel;
	private String assetType;
	private Integer assignmentId;
	private String assignedName;
	private String assignedBy;
	private String assignedTo;
	private Date assignedOn;
	private Date assignedTill;
	private String approveStatus;
	private Date lastUpdated;
	private String status;
	private Integer cost;
	private String categoryType;
	private String assetContract;
}
