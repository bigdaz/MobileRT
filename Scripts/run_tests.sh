###############################################################################
# Get arguments
###############################################################################
type="${1:-Release}"
ndk_version="${2:-21.0.6113669}"
cmake_version="${3:-3.6.0}"
###############################################################################
###############################################################################


###############################################################################
# Run unit tests natively
###############################################################################
output="$(./gradlew test${type}UnitTest \
  -DndkVersion="${ndk_version}" -DcmakeVersion="${cmake_version}")";
resUnitTests=$?
echo -e "output = ${output}"
###############################################################################
###############################################################################


###############################################################################
# Exit code
###############################################################################
echo "########################################################################"
echo "Results:"
if [ $resUnitTests -eq 0 ]; then
  echo "Unit tests: success"
else
  echo "Unit tests: failed"
  exit $resUnitTests
fi
###############################################################################
###############################################################################