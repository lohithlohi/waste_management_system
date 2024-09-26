package com.ust.wastewarden.routes.model;

import lombok.Data;

@Data
public class Bin {
    private double latitude;
    private double longitude;
    private int wasteAmount;
}
