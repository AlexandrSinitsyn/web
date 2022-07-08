package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", User.Color.GREEN),
            new User(6, "pashka", "Pavel Mavrin", User.Color.RED),
            new User(9, "geranazarov555", "Georgiy Nazarov", User.Color.RED),
            new User(11, "tourist", "Gennady Korotkevich", User.Color.BLUE)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Contest Round #345", "Contest will be started today at 16:00", 1),
            new Post(2, "Some fixes", "07-Oct-2021 00:23:15.938 INFO [RMI TCP Connection(59)-127.0.0.1] org.apache.jasper.servlet At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.", 6),
            new Post(3, "Contest Round #346", "Contest will be started the day after tomorrow at 18:00", 1),
            new Post(4, "Over 250 symbols", "bla-".repeat(1000), 9)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
