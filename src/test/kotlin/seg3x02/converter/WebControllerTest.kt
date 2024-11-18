package seg3x02.converter

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(WebController::class)
@Import(WebSecurityConfig::class)  
class WebControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @WithMockUser(username = "user1", password = "pass1", roles = ["USER"])
    fun request_to_home() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/")
            .with(csrf())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("home"))
    }

    @Test
    @WithMockUser(username = "user1", password = "pass1", roles = ["USER"])
    fun celsius_to_fahrenheit_conversion() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/convert")
                .param("celsius", "0")
                .param("fahrenheit", "")
                .param("operation", "CtoF")
                .with(csrf())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.model().attribute("fahrenheit", "32.00"))
            .andExpect(MockMvcResultMatchers.view().name("home"))
    }
}