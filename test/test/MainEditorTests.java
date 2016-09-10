/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import feather.editor.MainEditor;
import org.junit.*;

/**
 *
 * @author Milos Misic
 */
public class MainEditorTests {
    MainEditor mainEditor;

    @Before
    public void init() {
        mainEditor = new MainEditor();

    }
    public MainEditorTests() {
    }


    @Test

    public void shouldNotBeNull() {
        Assert.assertNotNull(mainEditor);

    }
}
