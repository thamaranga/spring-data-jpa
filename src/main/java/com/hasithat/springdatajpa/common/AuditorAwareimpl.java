package com.hasithat.springdatajpa.common;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/*
* This class is creted for generating values for Product objects createdBy and lastModifiedBy
* fields.
* */
public class AuditorAwareimpl implements AuditorAware {

    @Override
    public Optional getCurrentAuditor() {
        /*
        * If you are using spring security then first get the SecurityContextHolder
        * then get the principal
        * then get the user
        * and then set to below optional.
        * Since here I haven't used spring security I have hardcoded a name
        * */
        return Optional.of("Hasitha");
    }
}
