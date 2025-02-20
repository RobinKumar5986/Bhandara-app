package com.hometriangle.bhandara.ui.layouts.Home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.hometriangle.bhandara.MainApplication
import com.hometriangle.bhandara.data.models.BhandaraDto
import com.hometriangle.bhandara.ui.layouts.UiUtils.CenteredProgressView
import com.hometriangle.bhandara.ui.layouts.UiUtils.DatePickerField
import com.hometriangle.bhandara.ui.layouts.UiUtils.TimePickerField
import com.hometriangle.bhandara.ui.layouts.utils.convertImageToBase64Compress
import com.hometriangle.bhandara.ui.theme.DarkGrey
import com.hometriangle.bhandara.ui.theme.TrueWhite
import com.hometriangle.bhandara.ui.theme.defaultButtonColor
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Composable
fun CreateBhandaraScreen(
    nav: () -> Unit
) {
    // All the state values for the form fields
    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()
    val locations = viewModel.locations.collectAsState()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var dateOfBhandara by remember { mutableStateOf<Date?>(null) }
    var startingTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var endingTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var foodType by remember { mutableStateOf("veg") } // "veg" or "non-veg"
    var organizationType by remember { mutableStateOf("individual") }
    var organizationName by remember { mutableStateOf("") }
    var needVolunteer by remember { mutableStateOf(false) }
    var contactForVolunteer by remember { mutableStateOf("") }
    var specialNote by remember { mutableStateOf("") }
    var bhandaraType by remember { mutableStateOf("singleDay") } // "singleDay" or "everyDay"

    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val photoFile = File(outputDirectory, "captured_image.jpg")
    var isApiCallTriggered by remember { mutableStateOf(false) }
    var isLoading = viewModel.isLoading.collectAsState().value
    val fileUri = FileProvider.getUriForFile(
        context,
        "com.hometriangle.bhandara.provider",
        photoFile
    )

    // Create a scroll state for vertical scrolling
    val scrollState = rememberScrollState()
    val errorMsg = viewModel.error.collectAsState().value
    val addedBhandara = viewModel.addedBhandara.collectAsState().value

    if(!errorMsg.isNullOrEmpty()){
        Toast.makeText(context,errorMsg,Toast.LENGTH_SHORT).show()
    }
    if(addedBhandara != null){
        Toast.makeText(context,"Thank you for spreading kindness and joy. \uD83D\uDE4F❤\uFE0F",Toast.LENGTH_SHORT).show()
        viewModel.clearData()
        nav()
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }
    // Camera launcher to capture an image
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri = fileUri
            imageBitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
        }
    }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        if (isGranted) {
            cameraLauncher.launch(fileUri)
        }
    }

    // Wrap the entire form in a vertical scroll container
    if(!isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Top heading
            Text(
                text = "Create Bhandara",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Name [required]
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name *") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 2. Description [required]
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description *") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 3. Date of Bhandara [required]
            DatePickerField(
                selectedDate = dateOfBhandara,
                onDateSelected = { date ->
                    dateOfBhandara = date
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 4. Starting Time [required]
            TimePickerField(
                localDateTime = startingTime,
                placeHolder = "Select Starting Time *",
                onTimeSelected = { dateTime ->
                    startingTime = dateTime
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 5. Ending Time [required]
            TimePickerField(
                localDateTime = endingTime,
                placeHolder = "Select Ending Time *",
                onTimeSelected = { dateTime ->
                    endingTime = dateTime
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 6. Food Type [required]: Veg | Non-Veg
            Text(text = "Food Type *", style = MaterialTheme.typography.bodyLarge)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = foodType == "veg",
                    onClick = { foodType = "veg" }
                )
                Text(text = "Veg", modifier = Modifier.padding(start = 4.dp))
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = foodType == "non-veg",
                    onClick = { foodType = "non-veg" }
                )
                Text(text = "Non-Veg", modifier = Modifier.padding(start = 4.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))

            // 7. Organization Type [required]: Organization | Individual
            Text(text = "Organization Type *", style = MaterialTheme.typography.bodyLarge)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = organizationType == "organization",
                    onClick = { organizationType = "organization" }
                )
                Text(text = "Organization", modifier = Modifier.padding(start = 4.dp))
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = organizationType == "individual",
                    onClick = { organizationType = "individual" }
                )
                Text(text = "Individual", modifier = Modifier.padding(start = 4.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))

            // 8. Organization Name [if organizationType == "organization"]

            OutlinedTextField(
                value = organizationName,
                onValueChange = { organizationName = it },
                label = { if(organizationType == "organization") Text("Organization Name *") else Text("Individual Name *")},
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(8.dp))


            // 9. Need Volunteer (Checkbox)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = needVolunteer,
                    onCheckedChange = { needVolunteer = it }
                )
                Text(text = "Need Volunteer")
            }
            Spacer(modifier = Modifier.height(8.dp))

            // 10. Contact For Volunteer [Optional] (if volunteer is needed)
            if (needVolunteer) {
                OutlinedTextField(
                    value = contactForVolunteer,
                    onValueChange = { contactForVolunteer = it },
                    label = { Text("Contact For Volunteer (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // 11. Special Note [Optional]
            OutlinedTextField(
                value = specialNote,
                onValueChange = { specialNote = it },
                label = { Text("Special Note (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 12. Image [clickable]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)

                    .clickable {
                        if (hasCameraPermission) {
                            cameraLauncher.launch(fileUri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap!!.asImageBitmap(),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("+ Click to Upload Image")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // 13. Bhandara Type [required]: Single Day | Every Day
            Text(text = "Bhandara Type *", style = MaterialTheme.typography.bodyLarge)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = bhandaraType == "singleDay",
                    onClick = { bhandaraType = "singleDay" }
                )
                Text(text = "Single Day", modifier = Modifier.padding(start = 4.dp))
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = bhandaraType == "everyDay",
                    onClick = { bhandaraType = "everyDay" }
                )
                Text(text = "Every Day", modifier = Modifier.padding(start = 4.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Your Current Location", modifier = Modifier.padding(start = 4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            Color.LightGray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp)

                ) {
                    Text(
                        text = if (locations.value.isNotEmpty()) {
                            "${locations.value.first().latitude}"
                        } else {
                            "No location available"
                        },
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            Color.LightGray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp)

                ) {
                    Text(
                        text = if (locations.value.isNotEmpty()) {
                            "${locations.value.first().longitude}"
                        } else {
                            "No location available"
                        },
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Example submit button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = defaultButtonColor,
                    contentColor = TrueWhite,
                    disabledContainerColor = DarkGrey,
                    disabledContentColor = TrueWhite
                ),
                shape = MaterialTheme.shapes.small,
                onClick = {
                    if (name.isEmpty() || description.isEmpty() || dateOfBhandara == null || startingTime == null || imageUri == null) {
                        Toast.makeText(
                            context,
                            "Please fill all required fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (organizationName.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Please fill all required fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        isApiCallTriggered = true
                    }
                },
            ) {
                Text("Create")
            }
            if (isApiCallTriggered) {
                LaunchedEffect(Unit) {
                    isApiCallTriggered = false
                }
                if (dateOfBhandara != null && startingTime != null && endingTime != null && imageUri != null) {
                    ApiObserver(
                        name = name,
                        description = description,
                        dateOfBhandara = dateOfBhandara!!,
                        startingTime = startingTime!!,
                        endingTime = endingTime!!,
                        foodType = foodType,
                        organizationType = organizationType,
                        organizationName = organizationName,
                        needVolunteer = needVolunteer,
                        contactForVolunteer = contactForVolunteer,
                        specialNote = specialNote,
                        image = imageUri!!,
                        bhandaraType = bhandaraType,
                        latitude = locations.value.first().latitude,
                        longitude = locations.value.first().longitude,
                    )
                }
            }
        }
    }else{
        CenteredProgressView(message = "Creating bhandara please wait...")
    }
}


@SuppressLint("NewApi")
@Composable
fun ApiObserver(
    name: String,
    description: String,
    dateOfBhandara: Date,
    startingTime: LocalDateTime,
    endingTime: LocalDateTime,
    foodType: String,
    organizationType: String,
    organizationName: String,
    needVolunteer: Boolean,
    contactForVolunteer: String,
    specialNote: String,
    image: Uri,
    bhandaraType: String,
    latitude: Double,
    longitude: Double,
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()

    val base64Image = convertImageToBase64Compress(context, image)
    val userSharedPreferences = MainApplication.userDataPref
    val phone = userSharedPreferences.getString("phoneNumber","")
    val userId = userSharedPreferences.getString("userId","")

    // Convert Date and LocalDateTime to Double (Unix timestamp)
    val dateOfBhandaraDouble = dateOfBhandara.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val startingTimeDouble = startingTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli().toDouble()  // LocalDateTime to milliseconds
    val endingTimeDouble = endingTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli().toDouble()  // LocalDateTime to milliseconds


    val bhandaraDto = BhandaraDto(
        id = null,
        name = name,
        description = description,
        latitude = latitude,
        longitude = longitude,
        createdOn = null,
        updatedOn = null,
        dateOfBhandara = dateOfBhandaraDouble,
        startingTime = startingTimeDouble,
        endingTime = endingTimeDouble,
        verificationType = "UN_VERIFIED",
        foodType = foodType,
        organizationType = organizationType,
        organizationName = organizationName,
        phoneNumber = phone,
        needVolunteer = needVolunteer,
        contactForVolunteer = contactForVolunteer,
        specialNote = specialNote,
        createdBy = userId,
        image = base64Image ?: "",
        bhandaraType = bhandaraType
    )
    LaunchedEffect(Unit) {
        viewModel.addBhadara(bhandaraDto)
    }
}