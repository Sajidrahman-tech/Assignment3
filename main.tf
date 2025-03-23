provider "google" {
  project = "assignment-3-454605"
  region  = "us-central1"  # Choose your preferred region
}

resource "google_container_cluster" "primary" {
  name     = "assignmet3-cluster"
  location = "us-central1-a"  # Choose your preferred zone
  remove_default_node_pool = true
  initial_node_count       = 1

  network    = "default"
  subnetwork = "default"
}

resource "google_compute_disk" "persistent_disk" {
  name  = "saajid-disk"  # Fixed disk name to follow the naming convention
  type  = "pd-standard"   # Standard persistent disk
  size  = 1               # Size in GB
  zone  = "us-central1-a" # Ensure this matches the zone of your cluster
}

resource "google_container_node_pool" "primary_nodes" {
  name       = "node-pool"
  location   = google_container_cluster.primary.location
  cluster    = google_container_cluster.primary.name
  node_count = 1

  node_config {
    preemptible  = false
    machine_type = "e2-micro"
    image_type   = "COS_CONTAINERD"
    disk_size_gb = 10
    disk_type    = "pd-standard"
    oauth_scopes = [
      "https://www.googleapis.com/auth/cloud-platform"
    ]
  }

  management {
    auto_repair = true
    auto_upgrade = true
  }
}
