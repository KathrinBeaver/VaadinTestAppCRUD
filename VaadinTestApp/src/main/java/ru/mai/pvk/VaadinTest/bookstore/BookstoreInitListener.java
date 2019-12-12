package ru.mai.pvk.VaadinTest.bookstore;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import ru.mai.pvk.VaadinTest.bookstore.authentication.AccessControl;
import ru.mai.pvk.VaadinTest.bookstore.authentication.AccessControlFactory;
import ru.mai.pvk.VaadinTest.bookstore.ui.login.LoginScreen;

/**
 * This class is used to listen to BeforeEnter event of all UIs in order to
 * check whether a user is signed in or not before allowing entering any page.
 * It is registered in a file named
 * com.vaadin.flow.server.VaadinServiceInitListener in META-INF/services.
 */
public class BookstoreInitListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent initEvent) {
        final AccessControl accessControl = AccessControlFactory.getInstance()
                .createAccessControl();

        initEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                if (!accessControl.isUserSignedIn() && !LoginScreen.class
                        .equals(enterEvent.getNavigationTarget()))
                    enterEvent.rerouteTo(LoginScreen.class);
            });
        });
    }
}
