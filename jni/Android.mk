# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS) 
LOCAL_SHARED_LIBRARIES := libcutils
LOCAL_LDLIBS := -llog -landroid -lEGL -lGLESv1_CM

LOCAL_MODULE    := testLibrary
LOCAL_SRC_FILES := random.cpp graphic_native_activity.cpp renderer.cpp 2d.cpp 3d.cpp file.cpp float.cpp image.cpp integer.cpp matrix.cpp memory.cpp string.cpp video.cpp

include $(BUILD_SHARED_LIBRARY)

