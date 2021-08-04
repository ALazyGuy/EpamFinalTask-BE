package com.ltp.web.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractEntity implements Serializable {
    protected Long id;
}
