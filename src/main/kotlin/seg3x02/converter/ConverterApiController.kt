package seg3x02.converter

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ConverterApiController {

    @GetMapping("/convert")
    fun convert(
        @RequestParam(value = "celsius", required = false) celsius: String?,
        @RequestParam(value = "fahrenheit", required = false) fahrenheit: String?
    ): Map<String, String> {
        return try {
            when {
                !celsius.isNullOrEmpty() -> {
                    val celsiusVal = celsius.toDouble()
                    val fahrenheitVal = ((celsiusVal * 9) / 5 + 32)
                    mapOf("result" to String.format("%.2f", fahrenheitVal))
                }
                !fahrenheit.isNullOrEmpty() -> {
                    val fahrenheitVal = fahrenheit.toDouble()
                    val celsiusVal = ((fahrenheitVal - 32) * 5) / 9
                    mapOf("result" to String.format("%.2f", celsiusVal))
                }
                else -> mapOf("error" to "Please provide either celsius or fahrenheit parameter")
            }
        } catch (e: NumberFormatException) {
            mapOf("error" to "Invalid number format")
        }
    }
}