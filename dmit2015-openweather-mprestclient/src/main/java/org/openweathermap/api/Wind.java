
package org.openweathermap.api;

import jakarta.annotation.Generated;

@Generated("jsonschema2pojo")
public class Wind {

    private Double speed;
    private Integer deg;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }

}
