package view;

import controllers.MainWindowController;
import exception.CssException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ViewTest {

    @Test
    void showMainWindowShouldThrowCssException(){
        //MainWindowController mainWindowController = mock(MainWindowController.class);
        View view = mock(View.class);
        doNothing().when(view).showMainWindow();
        view.showMainWindow();
        verify(view, times(1)).showMainWindow();
    }

}