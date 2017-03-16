package org.fortiss.smg.informationbroker.impl.cache;


public class CacheKey {
		private String containerId; 
		
		public CacheKey() {
			
		}
		

		public CacheKey(String containerId) {
			//super();
			this.containerId = containerId;
			
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((containerId == null) ? 0 : containerId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CacheKey other = (CacheKey) obj;
			if (containerId == null) {
				if (other.containerId != null)
					return false;
			} else if (!containerId.equals(other.containerId))
				return false;
			return true;
		}

		public String getContainerId() {
			return containerId;
		}


		@Override
		public String toString() {
			return "CacheKey [containerId=" + containerId
					+ "]";
		}
	
		
}
