package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User( null, "Alex","alex@gmail.com", "123123", Role.ROLE_USER ),
            new User( null, "Bob","bob@gmail.com", "444222", Role.ROLE_USER ),
            new User( null, "John","john@gmail.com", "007007", Role.ROLE_USER ),
            new User( null, "Lera","lera@gmail.com", "000000", Role.ROLE_ADMIN, Role.ROLE_USER )
    );

    public static List<User> getSortNameUser( List<User> users )
    {
        return users.stream().sorted( Comparator.comparing( AbstractNamedEntity::getName ) ).collect( Collectors.toList() );
    }
}
